package com.example.maps.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PhotoEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "db_photo")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService databaseExecutorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getDatabaseExecutorService() {
        return databaseExecutorService;
    }

    public abstract PhotoDao getPhotoDao();

}



