package com.example.database.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.database.data.Phone;
import com.example.database.data.PhoneRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository mRepository;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones = mRepository.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return mRepository.getAllPhones();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(Phone phone) {
        mRepository.insert(phone);
    }

    public void delete(Phone phone) {
        mRepository.delete(phone);
    }

    public void update(Phone phone) {
        mRepository.update(phone);
    }


}
