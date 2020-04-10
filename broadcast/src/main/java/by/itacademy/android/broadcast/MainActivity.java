package by.itacademy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    public static final String BroadcastReceiverAction = "BroadcastReceiverAction";

    private CustomBroadcastReceiver customBroadcastReceiver;

    private BroadcastReceiver localBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastReceiverAction.equals(intent.getAction())) {
                Toast.makeText(context, "localBroadcastReceiver -> BroadcastReceiverAction", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBroadcastReceiver();
        registerLocalBroadcastReceiver();

        findViewById(R.id.sendBroadcastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomAction();
            }
        });
        findViewById(R.id.sendLocalBroadcastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocalAction();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // НЕ ЗАБЫВАЕМ СНЯТЬ РЕГИСТРАЦИЮ С ПРИЕМНИКА КОГДА АКТИВИТИ УНИЧТОЖАЕТСЯ
        if (customBroadcastReceiver != null) {
            unregisterReceiver(customBroadcastReceiver);
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastReceiver);
        super.onDestroy();
    }

    /**
     * Отправлям кастомное событие в ресивер, описанный в манифесте CustomManifestBroadcastReceiver
     * Проверяем версию операционной системы, на которой работает приложение, и, зависимости от версии ОС,
     * запускаем процесс отправки сообщения
     */
    private void sendCustomAction(){
        // Проверяем версию ОС
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(MainActivity.this, CustomManifestBroadcastReceiver.class);
            intent.setAction(CustomManifestBroadcastReceiver.CUSTOM_ACTION);
            sendBroadcast(intent);
        } else {
            sendBroadcast(new Intent(CustomManifestBroadcastReceiver.CUSTOM_ACTION));
        }
    }

    /**
     * Отправлям кастомное событие в локальный ресивер, созданный в коде
     * Проверяем версию операционной системы, на которой работает приложение, и, зависимости от версии ОС,
     * запускаем процесс отправки сообщения
     */
    private void sendLocalAction(){
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroadcastReceiverAction));
    }

    /**
     * Регистрируем ГЛОБАЛЬНЫЙ приемник сообщений в коде
     *
     * 1) Создаем фильтр событий, чтобы приемник не реагировал прямо на все события
     * 2) Создаем приемник и прокидываем в него фильтр
     * 3) Регистрируем приемник
     *
     * PS НЕ ЗАБЫВАЕМ СНЯТЬ РЕГИСТРАЦИЮ С ПРИЕМНИКА КОГДА АКТИВИТИ УНИЧТОЖАЕТСЯ !!!!!!!!!!! ()
     */
    private void registerBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CustomBroadcastReceiver.CUSTOM_ACTION);

        customBroadcastReceiver = new CustomBroadcastReceiver();
        registerReceiver(customBroadcastReceiver, intentFilter);
    }


    /**
     * Регистрируем ЛОКАЛЬНЫЙ приемник сообщений в коде
     *
     * 1) Создаем фильтр событий, чтобы приемник не реагировал прямо на все события
     * 2) Создаем приемник и прокидываем в него фильтр
     * 3) Регистрируем приемник
     *
     * PS НЕ ЗАБЫВАЕМ СНЯТЬ РЕГИСТРАЦИЮ С ПРИЕМНИКА КОГДА АКТИВИТИ УНИЧТОЖАЕТСЯ !!!!!!!!!!! ()
     */
    private void registerLocalBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastReceiverAction);

        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver, intentFilter);
    }
}
