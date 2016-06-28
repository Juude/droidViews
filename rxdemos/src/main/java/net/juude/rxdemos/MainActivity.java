package net.juude.rxdemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.juude.rxdemos.rx.RxAndroidFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new RxAndroidFragment())
                .commit();
    }
}
