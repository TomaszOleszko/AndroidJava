package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {
    public static final String MEAN_KEY =
            "com.example.w4_two_activities_and.MEAN_KEY";
    public static final String MSUBJECTS_KEY =
            "com.example.w4_two_activities_and.MSUBJECTS_KEY";

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

        Button meanButton = findViewById(R.id.meanButton);
        meanButton.setOnClickListener(view -> {
            double srednia = (double) mSubjects.stream().mapToInt(ModelOceny::getOcena).sum() / mSubjects.size();
            Bundle bundle = new Bundle(1);
            bundle.putString(MEAN_KEY,Double.toString(srednia));

            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    private void setSubjcetsInfo() {
        String[] subjects = getResources().getStringArray(R.array.subjects);
        int subjectCount = Integer.parseInt(getIntent().getStringExtra(MainActivity.GRADES_COUNT_KEY));

        for (int i = 0; i < subjectCount; i++) {
            mSubjects.add(new ModelOceny(subjects[i],2));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(MSUBJECTS_KEY,mSubjects);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSubjects = savedInstanceState.getParcelableArrayList(MSUBJECTS_KEY);
        setAdapter();
    }

    private void setAdapter() {
        InteraktywnyAdapterTablicy adapterTablicy = new InteraktywnyAdapterTablicy(this, mSubjects);
        recyclerView.setAdapter(adapterTablicy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}