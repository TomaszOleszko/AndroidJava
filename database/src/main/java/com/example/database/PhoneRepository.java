package com.example.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDao mPhoneDao;
    private LiveData<List<Phone>> mAllPhones;


    PhoneRepository(Application application) {
        PhoneRoomDatabase phoneRoomDatabase = PhoneRoomDatabase.getDatabase(application);
        mPhoneDao = phoneRoomDatabase.phoneDao();

        //mAllPhones = //odczytanie wszystkich elementow z dao

    }

    LiveData<List<Phone>> getAllPhones() {
        return null; //zwraca wszystkie telefony
    }

    void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            //skasowanie wszystkich elementow za pomoca obiektu DAO
        });
    }

}

