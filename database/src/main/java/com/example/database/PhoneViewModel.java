package com.example.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository mRepository;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones=null;
        //mAllPhones = //pobranie wszystkich metod z repozytorium
    }

    LiveData<List<Phone>> getAllPhones(){
        return null;
    }

    public void deleteAll(){

    }
}
