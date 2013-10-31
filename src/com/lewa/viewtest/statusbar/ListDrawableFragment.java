package com.lewa.viewtest.statusbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.drawable.ListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lewa.viewtest.R;

import java.util.Random;

public class ListDrawableFragment extends Fragment{

    protected static final String TAG = "ListDrawableFragment";
    private ListDrawable mStatusBarBackground;
    private View mStatusBarView;
    private Activity mActivity;
    private int mStatusBarBackgroundNextLevel;
    private Handler mHandler = new Handler();
    private int mDisableFlag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }
    
    private static final int[] STATUS_BAR_BACKGROUND_RESOURCES = {
        R.drawable.statusbar_background,
        R.drawable.statusbar_background_translucent,
        R.drawable.statusbar_background_transparent,
        R.drawable.statusbar_background_keyguard,
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.status_bar_test, null);
        mStatusBarView = v.findViewById(R.id.status_bar);
        v.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                StatusBarManager statusBar = (StatusBarManager)mActivity.getSystemService(Context.STATUS_BAR_SERVICE);
                mDisableFlag ^= (0x40000000 | StatusBarManager.DISABLE_CLOCK);
                statusBar.disable(mDisableFlag );
                Log.e(TAG, "mDisableFlag : " + mDisableFlag);
                updateStatusBarBackground();
         }
        });
        return v;
    }
    
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @SuppressLint("NewApi")
    private void updateStatusBarBackground() {
/*
        final int BACKGROUND_BLACK_LEVEL = 0;
        final int BACKGROUND_TRANSLUCENT_LEVEL = 1;
        final int BACKGROUND_TRANSPARENT_LEVEL = 2;
        final int BACKGROUND_KEYGUARD_LEVEL = 3;
*/
        if (mStatusBarBackground == null) {
            mStatusBarBackground = new ListDrawable(null, mActivity.getResources());

            for (int i = 0; i < STATUS_BAR_BACKGROUND_RESOURCES.length; i++) {
                mStatusBarBackground.addDrawable(STATUS_BAR_BACKGROUND_RESOURCES[i]);
            }
            mStatusBarBackground.setLevel(-1);
            mStatusBarView.setBackground(mStatusBarBackground);
        }
        mHandler.removeCallbacks(mEnableBackgroundRunnable);
        mStatusBarBackground.enableFade(true);
        
        Random r = new Random();
        mStatusBarBackgroundNextLevel = r.nextInt(STATUS_BAR_BACKGROUND_RESOURCES.length);

        if (mStatusBarBackgroundNextLevel != 0) {
            mHandler.postDelayed(mEnableBackgroundRunnable, 75);
        } else {
            mStatusBarBackground.enableFade(false);
            mHandler.postDelayed(mEnableBackgroundRunnable, 50);
        }
    }
    
    private Runnable mEnableBackgroundRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "set level : " + mStatusBarBackgroundNextLevel);
            mStatusBarBackground.setLevel(mStatusBarBackgroundNextLevel);
        }
    };
    
}
