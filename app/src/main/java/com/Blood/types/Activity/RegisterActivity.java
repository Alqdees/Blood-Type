package com.Blood.types.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.Blood.types.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;




public class RegisterActivity extends AppCompatActivity {
    private static final String A_PLUS = "A+";
    private static final String A_MINUS = "A-";
    private static final String B_PLUS = "B+";
    private static final String B_MINUS = "B-";
    private static final String AB_PLUS = "AB+";
    private static final String AB_MINUS = "A+";
    private static final String O_PLUS = "O+";
    private static final String O_MINUS = "O-";
    private com.google.android.material.textfield.TextInputEditText
            ET_name,ET_number, ET_location,Et_otp;
//    private LinearLayout linearLayout;
    private FirebaseFirestore db;
    private ActionBar actionBar;
    AutoCompleteTextView autoCompleteTextView;
    private androidx.constraintlayout.utils.widget.MotionButton register,confirm;
    private String name,number,type,location,deviceId;
//    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String[] types;
//    private String VerificationID;
    private Map<String, Object> users;
//    private FirebaseAuth auth;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // not change color in dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
         db = FirebaseFirestore.getInstance();
//         auth = FirebaseAuth.getInstance();
//         auth.setLanguageCode("EN");
        actionBar = getSupportActionBar();
//        linearLayout = findViewById(R.id.OTP);
//        linearLayout.setVisibility(View.GONE);
//        confirm = findViewById(R.id.confirm);
//        Et_otp = findViewById(R.id.code);

        types = new String[]{
                "A+",
                "B+",
                "A-",
                "B-",
                "O+",
                "O-",
                "AB+",
                "AB-",
                "لا أعرف"
        };
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,R.layout.drop_down_item,types);
        autoCompleteTextView = findViewById(R.id.typesAuto);
        autoCompleteTextView.setAdapter(arrayAdapter);

        actionBar.setTitle("تسجيل متبرع دم");
        ///////////////////////////////////////
        //deviceId = Add a new document with a constants Android ID
        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ///////////////////////////////////////

        // initialize variable

        ET_name = findViewById(R.id.name);
        ET_number = findViewById(R.id.number);
        ET_location = findViewById(R.id.location);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    name = ET_name.getText().toString();
                    number = ET_number.getText().toString();
                    type = autoCompleteTextView.getText().toString();
                    location = ET_location.getText().toString();

                    // here to check all the edit text to not Empty
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number) ||
                            TextUtils.isEmpty(type)
                            || TextUtils.isEmpty(location)) {

                        Toast.makeText(RegisterActivity.this, "أحد الحقول فارغ", Toast.LENGTH_SHORT).show();

                    }
                    //here to check length number phone
                    else if (number.length() < 11) {
                        Toast.makeText(RegisterActivity.this, "الرقم قصير", Toast.LENGTH_SHORT).show();
                    }
                    // this is to register user blood donation
                    else {
                        setData(name,number,type,location);
                    }

            }

        });
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String codOtp = Et_otp.getText().toString();
//                if (TextUtils.isEmpty(codOtp)){
//                    Toast.makeText(RegisterActivity.this, "حقل التأكيد فارغ", Toast.LENGTH_SHORT).show();
//                }else {
//                    verifycode(codOtp);
//                }
//            }
//        });

    }

    public void setData(String name, String number, String type, String location){

        users = new HashMap<>();
        users.put("name", name);
        users.put("number", number);
        users.put("type", type);
        users.put("location", location);
//        if (type.equals(A_PLUS)){
//
//        }
        DocumentReference docID = db.collection(type).document(deviceId);
        docID.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //Here to check if user device id is exist in fire store or not
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //if exist
                        Toast.makeText(RegisterActivity.this, "أنت مسجل بالفعل", Toast.LENGTH_SHORT).show();
                    } else {
                        // if not exist
                        db.collection(type)
                                .document(deviceId).set(users).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        onBackPressed();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    // here to error
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });





    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            final String code = phoneAuthCredential.getSmsCode();
//            if (code != null){
//                verifycode(code);
//            }
//        }
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s,
//                @NonNull PhoneAuthProvider.ForceResendingToken token) {
//            super.onCodeSent(s, token);
////            VerificationID = s;
//        }
//    };
////
////    private void verifycode(String code) {
////        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,code);
////        signInbyCredential(credential);
////
////    }
//
//    private void signInbyCredential(PhoneAuthCredential credential) {
//
//
//        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void sendVerifictionCode(String number) {
//
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber("+964"+number)
//                        .setTimeout(60L,TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(mCallbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}