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
       int id;
       switch (mListaOcen.get(numerWiersza).getOcena()){
           case 2:
               id = R.id.radioButton2;
               break;
           case 3:
               id = R.id.radioButton3;
               break;
           case 4:
               id = R.id.radioButton4;
               break;
           case 5:
               id = R.id.radioButton5;
               break;
           default:
               throw new IllegalStateException("Unexpected value: " + mListaOcen.get(numerWiersza).getOcena());
       }
       ocenyViewHolder.nameText.setText(subject);
       ocenyViewHolder.radioGroup.setTag(numerWiersza);
       ocenyViewHolder.radioGroup.check(id);
    }

    @Override
    public int getItemCount(){
     return mListaOcen.size();
    }

    public class OcenyViewHolder extends RecyclerView.ViewHolder implements  RadioGroup.OnCheckedChangeListener{
        private final TextView nameText;
        private RadioGroup radioGroup;

        public int getChecked() {
            return checked;
        }

        private int checked;

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
            RadioButton radioButton = radioGroup.findViewById(checkedID);
            int index = (Integer) radioGroup.getTag();
            checked = checkedID;
            mListaOcen.set(index, new ModelOceny(nameText.getText().toString(), Integer.parseInt(radioButton.getText().toString())));
        }

        public OcenyViewHolder(@NonNull View glownyElementWiersza){
            super(glownyElementWiersza);
            nameText = itemView.findViewById(R.id.modeltextView);
            radioGroup = itemView.findViewById(R.id.radioGroup1);
            radioGroup.setOnCheckedChangeListener(this);
        }
    }
}
