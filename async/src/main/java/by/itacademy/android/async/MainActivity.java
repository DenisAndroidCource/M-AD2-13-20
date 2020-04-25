package by.itacademy.android.async;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnStart);

//        handler = new Handler(getMainLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                button.setText(String.valueOf(msg.what));
//                return false;
//            }
//        });
//
//        Log.d("123", Thread.currentThread().getName());
//
//        HandlerThread handlerThread = new HandlerThread("asda");
//        handlerThread.start();
//        Looper looper = handlerThread.getLooper();
//        Handler handler = new Handler(looper);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("123", Thread.currentThread().getName());
//            }
//        });
//        handlerThread.quit();
//
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        };
//
//        Thread thread = new Thread(runnable);
//        thread.start();

        MyAsyncTask myAsyncTask = new MyAsyncTask(new WeakReference<MainActivity>(this));
        myAsyncTask.execute(2000L);
        myAsyncTask.cancel(false);

        Executor executor = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "sdasdasdasda";
            }
        }).thenAcceptAsync(new Consumer<String>() {
            @Override
            public void accept(String s) {
                button.setText(s);
            }
        }, ContextCompat.getMainExecutor(MainActivity.this));

    }

    @Deprecated
    private static class MyAsyncTask extends AsyncTask<Long, String, Boolean> {

        private WeakReference<MainActivity> weakReference;

        public MyAsyncTask(WeakReference<MainActivity> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weakReference.get() != null)
                Toast.makeText(weakReference.get(), "onPreExecute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Long... longs) {
            Long number = longs[0];

            publishProgress("Before sleep");

            try {
                publishProgress("Sleep");
                Thread.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress("AFTER sleep");
            return number % 2 == 0;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (weakReference.get() != null)
                Toast.makeText(weakReference.get(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (weakReference.get() != null)
                Toast.makeText(weakReference.get(), "FLAG is" + aBoolean, Toast.LENGTH_SHORT).show();
        }
    }
}
