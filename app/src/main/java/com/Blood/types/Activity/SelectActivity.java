package com.Blood.types.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Blood.types.R;

import meow.bottomnavigation.MeowBottomNavigation;

public class SelectActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private MotionButton line;
//    private MeowBottomNavigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_select);
        actionBar = getSupportActionBar();
        actionBar.hide();
        line = findViewById(R.id.lineTravel);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectActivity.this,Transportation_linesActivity.class));
            }
        });
//        navigation = findViewById(R.id.botton_navigation);
//        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_add));



    }
}