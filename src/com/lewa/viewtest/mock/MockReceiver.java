package com.lewa.viewtest.mock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.util.XmlUtils;
import com.lewa.viewtest.MainActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class MockReceiver extends BroadcastReceiver {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Mocker {
    }

    private static final String TAG = "MockReceiver";
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
    @SuppressWarnings({
            "rawtypes", "unchecked"
    })

    public void start(Context context, Intent intent) {
        String fragmentName = getString(intent.getExtras(), "fragment", "");
        List histories = null;
        try {
            histories = XmlUtils.readListXml(context.openFileInput(MainActivity.HISTORY_FILE));
        }
        catch (Exception e) {
            histories = new ArrayList<String>();
            Log.e(TAG, "", e);
        }
        histories.add(0, fragmentName);
        try {
            XmlUtils.writeListXml(histories, 
                    context.openFileOutput(MainActivity.HISTORY_FILE, Context.MODE_PRIVATE));
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        MockUtils.startFragment(context, fragmentName);
    }

}
