package com.Blood.types.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.Blood.types.Adapter.RecyclerViewAdapter;
import com.Blood.types.Model.Model;
import com.Blood.types.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
     ArrayList<Model> models;
     RecyclerViewAdapter adapter;
     RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReferenceFromUrl("https://blood-types-77ce2-default-rtdb.firebaseio.com/");
        models = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =  snapshot.child("User").child("name").getValue(String.class);
                String number = snapshot.child("User").child("number").getValue(String.class);
                String type = snapshot.child("User").child("type").getValue(String.class);
                Model model = new Model(name,number,type);
                models.add(model);
                adapter = new RecyclerViewAdapter(MainActivity.this,models);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
