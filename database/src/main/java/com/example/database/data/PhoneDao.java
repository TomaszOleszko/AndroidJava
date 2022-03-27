package com.example.database.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.database.data.Phone;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Phone phone);

    @Query("DELETE FROM Phones")
    void deleteAll();

    @Update
    void update(Phone phone);

    @Query("SELECT * FROM Phones ORDER BY producent ASC")
    LiveData<List<Phone>> getAlphabetizedPhonesByProducent();
}
