package com.example.maps.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photo")
    LiveData<List<PhotoEntity>> getAllLivaData();

    @Query("SELECT * FROM photo")
    List<PhotoEntity> getAll();

    @Query("SELECT * FROM photo WHERE id = :id")
    LiveData<PhotoEntity> getById(long id);

    @Insert(onConflict = REPLACE)
    long insert(PhotoEntity photoEntity);

    @Query("SELECT * FROM photo")
    Cursor getAllPhotosWithCursor();

    @Query("SELECT * FROM photo WHERE id = :id")
    Cursor getAllPhotoWithCursor(long id);
}
