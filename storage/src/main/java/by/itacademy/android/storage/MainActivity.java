package by.itacademy.android.storage;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 18000;
    private static final String PREF_KEY = "PREF_KEY";

    private EditText editText;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        String text = sharedPreferences.getString(PREF_KEY, "");
        editText.setText(text);

        findViewById(R.id.btnSaveToFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText(editText.getText().toString());
            }
        });

        findViewById(R.id.btnStartActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ReadFileActivity.newIntent(MainActivity.this));
            }
        });
    }

    @Override
    protected void onPause() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, editText.getText().toString());
        editor.apply();

        super.onPause();
    }

    private void saveText(String textToWrite) {
        File filesDir = getFilesDir();
        if (filesDir.isDirectory() && !filesDir.exists()) {
            filesDir.mkdir();
        }

        File textFile = new File(filesDir, "Recent.txt");
        if (textFile.isFile() && !textFile.exists()) {
            try {
                textFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(textFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append(textToWrite);
        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void checkStoragePermission() {
        int checkPermisionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermisionResult != PackageManager.PERMISSION_GRANTED) {
            String[] permissionArray = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissionArray, PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "I can ALREADY do that", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "I can do that", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission rejected", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
