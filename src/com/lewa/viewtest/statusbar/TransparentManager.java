package com.lewa.viewtest.statusbar;


import android.graphics.drawable.ListDrawable;
import android.os.Handler;
import android.view.View;

import com.lewa.viewtest.R;

public class TransparentManager {
    private View mView;
    private ListDrawable mBackgroundDrawable;
    private final static int DELAY_MILLS = 75;

    public TransparentManager(View v, BackgroundState state) {
        mView = v;
        mBackgroundDrawable = new ListDrawable(null, v.getContext().getResources());
        for (int i = 0; i < STATUS_BAR_BACKGROUND_RESOURCES.length; i++) {
            mBackgroundDrawable.addDrawable(STATUS_BAR_BACKGROUND_RESOURCES[i]);
        }
        mBackgroundDrawable.enableFade(true);
        mView.setBackgroundDrawable(mBackgroundDrawable);
        setState(state);
    } 
    
    private static final int[] STATUS_BAR_BACKGROUND_RESOURCES = {
        R.drawable.statusbar_background,
        R.drawable.statusbar_background_translucent,
        R.drawable.statusbar_background_transparent,
        R.drawable.statusbar_background_keyguard,
    };
    
    enum BackgroundState {
        
        STATE_BACKGROUND_BLACK(0), 
        STATE_BACKGROUND_TRANSLUCENT(1),
        STATE_BACKGROUND_TRANSPARENT(2),
        STATE_BACKGROUND_KEYGUARD(3);
        
        public int mState;
        BackgroundState(int state) {
            mState = state;
        }
    }
    
    public void setState(final BackgroundState state) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mBackgroundDrawable.setLevel(state.mState);
            }
           };
        mHandler.postDelayed(r, DELAY_MILLS);
        mHandler.removeCallbacks(r);
    }
    
    private static Handler mHandler = new Handler();
}
