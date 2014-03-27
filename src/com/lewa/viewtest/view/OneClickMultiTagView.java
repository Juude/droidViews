package com.lewa.viewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class OneClickMultiTagView extends LinearLayout {

    protected static final String TAG = "OneClickMultiTagView";

    public OneClickMultiTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onFinishInflate() {
        setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Log.e(TAG, "tag : " + v.getTag());
            }
        });
        super.onFinishInflate();
    }
}
