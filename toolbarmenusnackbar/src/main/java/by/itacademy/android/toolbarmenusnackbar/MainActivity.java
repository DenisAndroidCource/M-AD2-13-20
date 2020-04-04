package by.itacademy.android.toolbarmenusnackbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private View rootView;
    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.rootLayout);
        findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ToolbarActivity.newIntent(MainActivity.this));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.add(0, R.id.dynamicMenuItem, 1, "Dynamic menu item");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemShare:
                showSnackBarColoredMessageAndButton();
                break;
            case R.id.contact1:
                showShareToMessageSnackbar("Alex");
                break;
            case R.id.contact2:
                showShareToMessageSnackbar("Ivan");
                break;
            case R.id.contact3:
                showShareToMessageSnackbar("Vladimir");
                break;
            case R.id.dynamicMenuItem:
                showSnackbarMessage("dynamicMenuItem");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dismissSnackbar() {
        if (snackbar != null && snackbar.isShownOrQueued()) {
            snackbar.dismiss();
        }
    }

    private void showSnackbarMessage(String message) {
        dismissSnackbar();
        snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void showShareToMessageSnackbar(String name) {
        String message = String.format(getString(R.string.snackbar_shared), name);
        showSnackbarMessage(message);
    }

    private void showSnackBarColoredMessageAndButton() {
        dismissSnackbar();
        String message = getString(R.string.snackbar_bluetooth_message_error);
        snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snackbar_bluetooth_error_try_again, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBarColoredMessageAndButton();
            }
        });

        int tryAgainTextColor = ContextCompat.getColor(this, R.color.colorAccent);
        snackbar.setActionTextColor(tryAgainTextColor);

        int messageColor = ContextCompat.getColor(this, R.color.colorError);
        TextView textView = snackbar.getView()
                .findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(messageColor);
        snackbar.show();
    }
}
