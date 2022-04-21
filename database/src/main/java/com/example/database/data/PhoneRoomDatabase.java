package com.example.database.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract PhoneDao phoneDao();

    private static volatile PhoneRoomDatabase INSTANCE;

    static PhoneRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhoneRoomDatabase.class, "DatabaseName").addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();
                Arrays.asList(
                            new Phone(0, "Samsung", "Galaxy-S22", "11.0", "https://www.samsung.com/pl/smartphones/galaxy-s22/"),
                            new Phone(0, "Samsung", "Galaxy Z Fold3 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-z-fold3-5g/"),
                            new Phone(0, "Samsung", "Galaxy Z Flip3 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-z-flip3-5g/"),
                            new Phone(0, "Samsung", "Galaxy M52 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-m/galaxy-m52-5g-light-blue-128gb-sm-m526blbdeue/"),
                            new Phone(0, "Samsung", "Galaxy A53 5G", "9.0", "https://www.samsung.com/pl/smartphones/galaxy-a/galaxy-a53-5g-awesome-blue-128gb-sm-a536blbneue/"),
                            new Phone(0, "ASUS", "Zenfone 8", "10.0", "https://www.asus.com/pl/Mobile/Phones/ZenFone/Zenfone-8/"),
                            new Phone(0, "ASUS", "ROG Phone 5s Pro", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5s-pro-model/"),
                            new Phone(0, "ASUS", "ROG Phone 5s", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5s-model/"),
                            new Phone(0, "ASUS", "ROG Phone 5s Ultimate", "10.0", "https://rog.asus.com/pl/phones/rog-phone-5-ultimate-model/"),
                            new Phone(0, "Nokia", "Nokia-G21", "11.0", "https://www.nokia.com/phones/pl_pl/nokia-g-21")
                    ).forEach(dao::insert);
            });
        }
    };
}