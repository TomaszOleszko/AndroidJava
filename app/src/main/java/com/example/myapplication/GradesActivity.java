package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        int subjectCount = Integer.parseInt(getIntent().getStringExtra(MainActivity.TEXT_KEY));

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