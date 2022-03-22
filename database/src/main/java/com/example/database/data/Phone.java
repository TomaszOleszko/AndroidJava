package com.example.database.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Phones")
public class Phone {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phone_id")
    public long mPhone_id;

    @NonNull
    @ColumnInfo(name = "producent")
    public String mProducent;

    @NonNull
    @ColumnInfo(name = "model")
    public String mModel;

    @NonNull
    @ColumnInfo(name = "wersja_androida")
    public String mAndroidVersion;

    @NonNull
    @ColumnInfo(name = "strona_www")
    public String mStronaWWW;


    public Phone(long mPhone_id, @NonNull String mProducent, @NonNull String mModel, @NonNull String mAndroidVersion, @NonNull String mStronaWWW) {
        this.mPhone_id = mPhone_id;
        this.mProducent = mProducent;
        this.mModel = mModel;
        this.mAndroidVersion = mAndroidVersion;
        this.mStronaWWW = mStronaWWW;
    }


}
