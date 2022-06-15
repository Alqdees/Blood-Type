package com.Blood.types.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.Blood.types.Adapter.RecyclerViewAdapter;
import com.Blood.types.Model.Model;
import com.Blood.types.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<Model> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Model model = new Model(
                "Ahmed",
                "Ahmed",
                "Ahmed",
                "Ahmed"

        );
        models.add(model);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,models);
        recyclerView.setAdapter(adapter);
    }
}