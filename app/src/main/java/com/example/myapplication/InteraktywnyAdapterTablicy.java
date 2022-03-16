package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {
    private List<ModelOceny> mListaOcen;
    private LayoutInflater mPompka;

    public InteraktywnyAdapterTablicy(Activity kontekst, LayoutInflater mPompka) {
        this.mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = mListaOcen;
    }

    @NonNull
    @Override
    public InteraktywnyAdapterTablicy.OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mPompka.inflate(R.layout.activity_grades,null);
        return new OcenyViewHolder(wiersz);

    }

    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int numerWiersza){

    }

    @Override
    public int getItemCount(){
     return mListaOcen.size();
    }

    public class OcenyViewHolder extends RecyclerView.ViewHolder implements  RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

        }

        public OcenyViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
        }
    }
}
