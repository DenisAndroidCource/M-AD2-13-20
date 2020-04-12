package by.itacademy.android.services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAG1 = "by.itacademy.android.services.CUSTOM_ACTION";

    private SimpleService simpleService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SimpleService.SimpleServiceBinder binder = (SimpleService.SimpleServiceBinder) iBinder;
            simpleService = binder.getService();
            Log.d(TAG, "onServiceConnected: " + componentName.getClass().getName());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: " + componentName.getClass().getName());
            simpleService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        findViewById(R.id.btnStopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSimpleService();
            }
        });

        findViewById(R.id.btnBindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind();
            }
        });

        findViewById(R.id.btnUnBindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbind();
            }
        });

        findViewById(R.id.btnServiceGetResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simpleService != null) {
                    Log.d(TAG, simpleService.getResult());
                }
            }
        });
    }

    private void startService() {
        Intent intent = new Intent(this, SimpleService.class);
        intent.putExtra(SimpleService.EXTRA_DATA, "Hello service");
        startService(intent);
    }

    private void stopSimpleService() {
        Intent intent = new Intent(this, SimpleService.class);
        stopService(intent);
    }

    private void bind() {
        Intent intent = new Intent(this, SimpleService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbind(){
        unbindService(serviceConnection);
    }
}
