package by.itacademy.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog dialog;
    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    showToast("Positive button clicked");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    showToast("Negative button clicked");
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    showToast("Neutral button clicked");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnShowDialog).setOnClickListener(this);
        findViewById(R.id.btnShowCustomDialog).setOnClickListener(this);
        findViewById(R.id.btnShowDynamicDialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShowDialog:
                showDialog();
                break;
            case R.id.btnShowCustomDialog:
                showCustomDialog();
                break;
            case R.id.btnShowDynamicDialog:
                showDynamicDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void showDialog() {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialogTitle))
                .setMessage(getString(R.string.dialogMessage))
                .setPositiveButton(R.string.dialogButtonPositive, dialogClickListener)
                .setNegativeButton(R.string.dialogButtonNegative, dialogClickListener)
                .setNeutralButton(R.string.dialogButtonNeutral, dialogClickListener);

        dialog = builder.create();
        dialog.show();
    }

    private void showCustomDialog() {
        dismissDialog();

        final EditText view = (EditText) LayoutInflater.from(this)
                .inflate(R.layout.view_dialog_edittext, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(getString(R.string.dialogTitle))
                .setMessage(getString(R.string.dialogMessage))
                .setPositiveButton(R.string.dialogButtonPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        openWebPage(view.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialogButtonNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    private void showDynamicDialog() {
        dismissDialog();

        final EditText view = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(getString(R.string.dialogTitle))
                .setMessage(getString(R.string.dialogMessage))
                .setPositiveButton(R.string.dialogButtonPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        openWebPage(view.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialogButtonNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
