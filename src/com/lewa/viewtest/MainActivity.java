package com.lewa.viewtest;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoRecents(null);
    }

    public void gotoRecents(View v) {
        Intent i = new Intent(this, CommonActivity.class);
        i.putExtra("fragment", "com.lewa.viewtest.recents.RecentsFragment");
        startActivity(i);
    }
    
    
}
