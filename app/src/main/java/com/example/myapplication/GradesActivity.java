package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {
    private ArrayList<ModelOceny> mSubjects;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        recyclerView = findViewById(R.id.lista_ocen_rv);
        mSubjects = new ArrayList<>();

        setSubjcetsInfo();
        setAdapter();



    }



    private void setSubjcetsInfo() {
        String[] subjects = getResources().getStringArray(R.array.subjects);
        int subjectCount = Integer.parseInt(getIntent().getStringExtra(MainActivity.GRADES_COUNT_KEY));

        for (int i = 0; i < subjectCount; i++) {
            mSubjects.add(new ModelOceny(subjects[i],2));
        }
    }

    private void setAdapter() {
        InteraktywnyAdapterTablicy adapterTablicy = new InteraktywnyAdapterTablicy(this, mSubjects);
        recyclerView.setAdapter(adapterTablicy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}