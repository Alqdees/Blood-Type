package com.Blood.types.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Blood.types.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText
            ET_name,ET_number,ET_type, ET_location;
    private FirebaseFirestore db;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ActionBar actionBar;
    private androidx.constraintlayout.utils.widget.MotionButton register;
    private String name,number,type,location,deviceId;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         db = FirebaseFirestore.getInstance();
        actionBar = getSupportActionBar();
        preferences = getSharedPreferences("MyBlood",MODE_PRIVATE);
        editor = preferences.edit();

        actionBar.setTitle("تسجيل متبرع دم");
        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ET_name = findViewById(R.id.name);
        ET_number = findViewById(R.id.number);
        ET_type = findViewById(R.id.type);
        ET_location = findViewById(R.id.loction);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
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

    }
    private boolean showDialog(){
        View v = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.dailog_auth,null,false);
        EditText et_Code = v.findViewById(R.id.code);
        MotionButton confirm = v.findViewById(R.id.confirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        builder.setTitle("تاكيد رقم الهاتف");
        return true;
    }
}