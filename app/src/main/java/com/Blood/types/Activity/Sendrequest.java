package com.Blood.types.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.Blood.types.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sendrequest extends AppCompatActivity {


    private TextInputEditText nameET,numberET,
            specializationET,timeET,titleET;
    private MotionButton sendRequest;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendrequest);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("أضافة طبيب");
        nameET = findViewById(R.id.name);
        numberET = findViewById(R.id.number);
        specializationET = findViewById(R.id.specialization);
        timeET = findViewById(R.id.time);
        titleET = findViewById(R.id.title);
        sendRequest= findViewById(R.id.addRequest);
        ref = FirebaseDatabase.
                getInstance().getReferenceFromUrl
                        ("https://blood-types-77ce2-default-rtdb.firebaseio.com/");
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString().trim();
                String number = numberET.getText().toString();
                String specialization = specializationET.getText().toString();
                String time = timeET.getText().toString();
                String title = titleET.getText().toString();
                @SuppressLint("HardwareIds")
                String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                ref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ref.child("User").child(deviceId).child("userName").setValue(name);
                        ref.child("User").child(deviceId).child("number").setValue(number);
                        ref.child("User").child(deviceId).child("time").setValue(time);
                        ref.child("User").child(deviceId).child("specialization").setValue(specialization);
                        ref.child("User").child(deviceId).child("title").setValue(title);
                        Toast.makeText(Sendrequest.this,
                                "تم اراسال الطلب", Toast.LENGTH_SHORT).show();
                        nameET.setText("");
                        numberET.setText("");
                        specializationET.setText("");
                        timeET.setText("");
                        titleET.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(Sendrequest.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });


    }
}