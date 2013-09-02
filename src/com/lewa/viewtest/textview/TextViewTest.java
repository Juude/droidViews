package com.lewa.viewtest.textview;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lewa.viewtest.R;

public class TextViewTest extends ViewGroup{
    
    public final String TAG = "TextViewTest";
    
    public TextViewTest(Context context) {
        this(context, null, 0);
    }
    
    public TextViewTest(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    
    public TextViewTest(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    
    @Override
    protected void onFinishInflate() {
        Context context = getContext();
        TextView textSpanTest = (TextView)findViewById(R.id.textSpanTest);
        Object [] args = new Integer[2];
        args[0] = 0;
        args[1] = 1;
        String str = context.getString(R.string.phone, args); 
        SpannableString spannablecontent=new SpannableString(str);
        int start = 0;
        for(int i=0; i < args.length; i++) {
            String value = String.valueOf(args[i]);
            int pStart = str.indexOf(value, start);
            int pEnd = pStart + value.length();
            start = pEnd;
            int intValue = (Integer) args[i];
            if(intValue > 0) {
                spannablecontent.setSpan(new ForegroundColorSpan(Color.BLUE), pStart, pEnd, 0);
            }
        }
        textSpanTest.setText(spannablecontent);
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i=0; i<getChildCount(); i++){
            View v = getChildAt(i);
            Log.d(TAG, "Measured Width / Height: "+getMeasuredWidth()+", "+getMeasuredHeight());            
            v.layout(l,t, r, b);
        } 
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i=0; i<getChildCount(); i++){
            View v = getChildAt(i);
            Log.d(TAG, "Measured Width / Height: "+getMeasuredWidth()+", "+getMeasuredHeight());            
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
