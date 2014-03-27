package com.lewa.viewtest.mock;

import android.content.Context;
import android.os.Bundle;

public abstract class Mocker {
    protected Context mContext;
    protected Bundle mExtras;
    public Mocker (Context context, Bundle extras) {
        mContext = context;
        mExtras = extras;
    }
    
    public void setArgs(Bundle extras) {
        mExtras = extras;
    }
    public abstract void dump();
}
