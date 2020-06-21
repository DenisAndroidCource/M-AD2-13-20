package com.gmail.denislatushko92.contentresolverexample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver contentResolver = getContentResolver();

        List<File> fileList = new ArrayList<>();

        Uri uri = Uri.parse("content://com.example.maps.PHOTO_PROVIDER/PHOTO/ALL");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int filePath = cursor.getColumnIndex("filePath");

            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(filePath));
                fileList.add(file);
            }
        }

//        if (contentResolver.delete() == -1) {
//            Toast.makeText(this, "Not deleted", Toast.LENGTH_LONG).show();
//        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("CONTENT_ID", 10L);

        if (contentResolver.insert(uri, contentValues) == null) {



            Toast.makeText(this, "Not deleted", Toast.LENGTH_LONG).show();
        }
    }
}
