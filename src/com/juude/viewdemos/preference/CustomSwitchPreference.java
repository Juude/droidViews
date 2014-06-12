package com.juude.viewdemos.preference;

import com.juude.viewdemos.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import lewa.internal.v5.widget.SlidingButton;
import lewa.internal.v5.widget.SlidingButton.OnCheckedChangedListener;

public class CustomSwitchPreference {
    
    
    protected static final String TAG = "CustomSwitchPreference";

    public static View inflateCheckBoxPreference(LayoutInflater inflater, 
            Context context) {
        final View view = inflater.inflate(R.layout.preference, null, false);
        final LinearLayout widgetFrame = (LinearLayout) view
                .findViewById(android.R.id.widget_frame);
        final SlidingButton sliding = new SlidingButton(context);
        widgetFrame.addView(sliding, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Log.e(TAG, " main clicked");
                sliding.setChecked(!sliding.isChecked());
            }
        });
        
        sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "checkbox clicked");
            }
        });
        sliding.setOnCheckedChangedListener(new OnCheckedChangedListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                Log.e(TAG, "sliding checkchange " + checked);
                sliding.setChecked(!checked);
            }
            
        });
        
        sliding.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                Log.e(TAG, "compound checkchange " + checked);  
            }
            
        });
        return view;
    }
    
    class Listener implements OnCheckedChangeListener, OnCheckedChangedListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
        
    }
    private static void requestChange(boolean b) {
        
    }

}
