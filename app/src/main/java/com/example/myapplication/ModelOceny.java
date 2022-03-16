package com.example.myapplication;

public class ModelOceny {
    private String nazwa;
    private int ocena;

    public ModelOceny(String nazwa, int ocena) {
        if (ocena < 2 || ocena > 5){
             this.ocena = 2;
        }
        this.nazwa = nazwa;
        this.ocena = ocena;
    }

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
}
