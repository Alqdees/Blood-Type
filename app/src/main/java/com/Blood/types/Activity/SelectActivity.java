package com.Blood.types.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.StatusBarManager;
import android.os.Bundle;

import com.Blood.types.R;

public class SelectActivity extends AppCompatActivity {

    private ActionBar actionBar;
//    statusBar
    private StatusBarManager statusBarManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        actionBar = getSupportActionBar();

        actionBar.hide();



    }
}