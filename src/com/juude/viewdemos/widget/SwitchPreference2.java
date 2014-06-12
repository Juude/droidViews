package com.juude.viewdemos.widget;

import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.util.Log;

public class SwitchPreference2 extends SwitchPreference{

    public static final String TAG = "SwitchPreference2";

    public SwitchPreference2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SwitchPreference2(Context context) {
        this(context, null, 0);
    }
    
    public SwitchPreference2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onClick() {
        Log.e(TAG, "onClicked");
        super.onClick();
    }
    
    
}
