package com.example.database.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.database.data.Phone;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert()
    void insert(Phone phone);

    @Query("DELETE FROM Phones")
    void deleteAll();

    @Query("SELECT * FROM Phones ORDER BY producent ASC")
    LiveData<List<Phone>> getAlphabetizedPhonesByProducent();
}
