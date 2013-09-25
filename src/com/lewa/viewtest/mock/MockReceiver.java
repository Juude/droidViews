package com.lewa.viewtest.mock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lewa.viewtest.CommonActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MockReceiver extends BroadcastReceiver {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Mocker {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
           final String action = intent.getStringExtra("action");
           try {
               MockReceiver.class.getMethod(action, Context.class, Intent.class)
               .invoke(this, context, intent);
           }
           catch (Exception e) {
               
           }
    }
    
    public static String getString(Bundle extras, String key, String def) {
        String result = extras.getString(key);
        if(result == null) {
            result = def;
        }
        return result;
    }
    
    public void start(Context context, Intent intent) {
        Intent i = new Intent(context, CommonActivity.class);
        i.putExtra(CommonActivity.KEY_FRAGMENT, getString(intent.getExtras(), "fragment", ""));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
