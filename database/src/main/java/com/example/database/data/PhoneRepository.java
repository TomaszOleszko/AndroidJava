package com.example.database.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.database.data.Phone;
import com.example.database.data.PhoneDao;
import com.example.database.data.PhoneRoomDatabase;

import java.util.List;

public class PhoneRepository {
    private PhoneDao mPhoneDao;
    private LiveData<List<Phone>> mAllPhones;


    PhoneRepository(Application application) {
        PhoneRoomDatabase phoneRoomDatabase = PhoneRoomDatabase.getDatabase(application);
        mPhoneDao = phoneRoomDatabase.phoneDao();

        mAllPhones = mPhoneDao.getAlphabetizedPhonesByProducent();
    }

    LiveData<List<Phone>> getAllPhones() {
        return mAllPhones; //zwraca wszystkie telefony
    }

    void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.deleteAll());
    }

    void insert(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.insert(phone));
    }

    void delete(Phone phone){
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.delete(phone));
    }

    void update(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.update(phone));
    }

}

