package net.juude.widgetsdemos.alertdialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by sjd on 16/7/23.
 */
public class DialogActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog
                .Builder(this)
                .setMessage("test")
                .show();
    }
}
