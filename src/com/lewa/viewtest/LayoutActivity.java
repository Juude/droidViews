
package com.lewa.viewtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LayoutActivity extends Activity {

    public static final String KEY_LAYOUT = "layout";
    private static final String TAG = "LayoutActivity";
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e(TAG, "" + bundle);
        final String layout = getIntent().getStringExtra(KEY_LAYOUT);
        int contentViewId = getResources().getIdentifier(layout, "layout", getPackageName());
        setContentView(contentViewId);
    }
}
