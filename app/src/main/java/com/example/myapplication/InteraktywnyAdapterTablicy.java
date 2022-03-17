package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {

    private ArrayList<ModelOceny> mListaOcen;
    private LayoutInflater mPompka;

    public InteraktywnyAdapterTablicy(Activity kontekst, ArrayList<ModelOceny> ListaOcen) {
        this.mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = ListaOcen;
    }

    @NonNull
    @Override
    public InteraktywnyAdapterTablicy.OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mPompka.inflate(R.layout.model,parent,false);
        return new OcenyViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int numerWiersza){
       String subject = mListaOcen.get(numerWiersza).getNazwa();
       ocenyViewHolder.nameText.setText(subject);
    }

    @Override
    public int getItemCount(){
     return mListaOcen.size();
    }

    public class OcenyViewHolder extends RecyclerView.ViewHolder implements  RadioGroup.OnCheckedChangeListener{
        private final TextView nameText;
        private RadioGroup radioGroup;

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
            RadioButton radioButton = radioGroup.findViewById(checkedID);
        }

        public OcenyViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
            nameText = itemView.findViewById(R.id.modeltextView);
            radioGroup = itemView.findViewById(R.id.radioGroup1);

            radioGroup.setOnCheckedChangeListener(this);
        }
    }
}
