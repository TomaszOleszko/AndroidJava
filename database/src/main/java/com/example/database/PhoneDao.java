package com.example.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

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
