package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelOceny implements Parcelable {
    private String nazwa;
    private int ocena;

    public ModelOceny(String nazwa, int ocena) {
        if (ocena < 2 || ocena > 5){
             this.ocena = 2;
        }
        this.nazwa = nazwa;
        this.ocena = ocena;
    }

    protected ModelOceny(Parcel in) {
        nazwa = in.readString();
        ocena = in.readInt();
    }

    public static final Creator<ModelOceny> CREATOR = new Creator<ModelOceny>() {
        @Override
        public ModelOceny createFromParcel(Parcel in) {
            return new ModelOceny(in);
        }

        @Override
        public ModelOceny[] newArray(int size) {
            return new ModelOceny[size];
        }
    };

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nazwa);
        parcel.writeInt(ocena);
    }
}
