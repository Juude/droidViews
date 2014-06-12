package com.juude.viewdemos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import lewa.internal.v5.widget.SlidingButton;

public class SlidingButtonImproved extends SlidingButton{

    public SlidingButtonImproved(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlidingButtonImproved(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public SlidingButtonImproved(Context context) {
        super(context, null, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isClickable()) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    
    
}
