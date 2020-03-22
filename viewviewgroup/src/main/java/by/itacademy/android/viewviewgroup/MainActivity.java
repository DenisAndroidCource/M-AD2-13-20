package by.itacademy.android.viewviewgroup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText viewNumber1Text;
    private EditText viewNumber2Text;
    private TextView viewResultText;
    private Button viewButtonCalculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewNumber1Text = findViewById(R.id.viewNumber1Text);
        viewNumber2Text = findViewById(R.id.viewNumber2Text);
        viewResultText = findViewById(R.id.viewResultText);

        viewButtonCalculate = findViewById(R.id.viewButtonCalculate);
        viewButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = Integer.parseInt(viewNumber1Text.getText().toString());
                int number2 = Integer.parseInt(viewNumber2Text.getText().toString());

                int result = number1 * number2;

                viewResultText.setText(String.valueOf(result));
            }
        });

        Button viewButtonOpenCustomView = findViewById(R.id.viewButtonOpenCustomView);
        viewButtonOpenCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CustomViewActivity.newIntent(MainActivity.this));
            }
        });
    }
}
