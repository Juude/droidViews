package com.lewa.viewtest.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TestIncludeView extends RelativeLayout{

    public TestIncludeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        
        //findViewById(com.lewa.viewtest.R.id.include).setBackgroundColor(Color.RED);
        findViewById(com.lewa.viewtest.R.id.included).setBackgroundColor(Color.GREEN);

        super.onFinishInflate();
    }
    

    

}
