package com.Blood.types.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.Blood.types.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class RegisterActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText
            ET_name,ET_number,ET_type, ET_location,Et_otp;
//    private LinearLayout linearLayout;
    private FirebaseFirestore db;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ActionBar actionBar;
    private androidx.constraintlayout.utils.widget.MotionButton register,confirm;
    private String name,number,type,location,deviceId;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private String VerificationID;
    private FirebaseAuth auth;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         db = FirebaseFirestore.getInstance();
//         auth = FirebaseAuth.getInstance();
//         auth.setLanguageCode("EN");
        actionBar = getSupportActionBar();
//        linearLayout = findViewById(R.id.OTP);
//        linearLayout.setVisibility(View.GONE);
//        confirm = findViewById(R.id.confirm);
//        Et_otp = findViewById(R.id.code);



        preferences = getSharedPreferences("MyBlood",MODE_PRIVATE);
        editor = preferences.edit();
        actionBar.setTitle("تسجيل متبرع دم");
        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ET_name = findViewById(R.id.name);
        ET_number = findViewById(R.id.number);
        ET_type = findViewById(R.id.type);
        ET_location = findViewById(R.id.location);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = ET_name.getText().toString();
                number = ET_number.getText().toString();
                type = ET_type.getText().toString();
                location = ET_location.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)
                        || TextUtils.isEmpty(type) || TextUtils.isEmpty(location)) {

                    Toast.makeText(RegisterActivity.this, "أحد الحقول فارغ", Toast.LENGTH_SHORT).show();

                }
                else if (number.length() < 11) {
                    Toast.makeText(RegisterActivity.this, "الرقم قصير", Toast.LENGTH_SHORT).show();
                }
//
//
//
//                sendVerifictionCode(number);
//                linearLayout.setVisibility(View.VISIBLE);
                else {

                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("number", number);
                        user.put("type", type);
                        user.put("location", location);

                        //deviceId = Add a new document with a constants Android ID
                        db.collection("User")
                                .document(deviceId).set(user).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        editor.putString("name",name);
                                        editor.putString("number",number);
                                        editor.putString("type",type);
                                        editor.commit();
                                        Snackbar.make(v, "تمت الاضافة", Snackbar.LENGTH_LONG).show();
                                        onBackPressed();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });



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
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            VerificationID = s;
        }
    };

    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,code);
        signInbyCredential(credential);

    }

    private void signInbyCredential(PhoneAuthCredential credential) {


        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendVerifictionCode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+964"+number)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Toast.makeText(this, "أنت مسجل فعلاَ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}