package com.juude.viewdemos.view;

import com.juude.viewdemos.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class DuplicateIdView extends LinearLayout {

    public DuplicateIdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public DuplicateIdView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }    
    
    public DuplicateIdView(Context context) {
        super(context, null, 0);
    }

    @Override
    protected void onFinishInflate() {
        View v = findViewById(R.id.duplicateId);
        v.setBackgroundColor(Color.RED);
        super.onFinishInflate();
    }
    
}
