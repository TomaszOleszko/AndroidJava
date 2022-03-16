package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        String[] subjects = getResources().getStringArray(R.array.subjects);
        int subjectCount = Integer.parseInt(getIntent().getStringExtra(MainActivity.TEXT_KEY));


    }
}