package com.example.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Phones")
@Getter
@Setter
public class Phone implements Parcelable {

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

    protected Phone(Parcel in) {
        mPhone_id = in.readLong();
        mProducent = in.readString();
        mModel = in.readString();
        mAndroidVersion = in.readString();
        mStronaWWW = in.readString();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mPhone_id);
        parcel.writeString(mProducent);
        parcel.writeString(mModel);
        parcel.writeString(mAndroidVersion);
        parcel.writeString(mStronaWWW);
    }
}
