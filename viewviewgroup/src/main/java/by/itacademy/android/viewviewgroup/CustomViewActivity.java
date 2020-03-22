package by.itacademy.android.viewviewgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomViewActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, CustomViewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        CustomView customView = findViewById(R.id.customView);

        customView.setListener(new CustomView.onMeasureListener() {
            @Override
            public void onSizeChanged(int width, int height) {
                String message = String.format("width: %d height: %d", width, height);
                Toast.makeText(CustomViewActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
