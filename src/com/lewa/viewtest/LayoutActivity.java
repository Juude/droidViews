
package com.lewa.viewtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LayoutActivity extends Activity {

    public static final String KEY_LAYOUT = "com.android.contacts.KEY_LAYOUT";
    private static final String TAG = "LayoutActivity";
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e(TAG, "" + bundle);
        setContentView(bundle.getInt(KEY_LAYOUT));
    }
}
