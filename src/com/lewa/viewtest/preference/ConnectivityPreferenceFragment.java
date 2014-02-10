package com.lewa.viewtest.preference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.telephony.TelephonyIntents;
import com.lewa.viewtest.R;
import com.lewa.viewtest.widget.SwitchPreference2;

public class ConnectivityPreferenceFragment extends PreferenceFragment{
    private static final String TAG = "PreferenceTestFragment";
    protected static final int UPDATE_STATE = 0;
    private SwitchPreference mMobilePreference;
    private ConnectivityManager mConnService;
    private Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case UPDATE_STATE:
                        updateState();
                        break;
                }
                super.handleMessage(msg);
            }
            
        };
        mMobilePreference = (SwitchPreference) findPreference("mobile_data_preference");
        getActivity().registerReceiver(mReceiver, 
                new IntentFilter(TelephonyIntents.ACTION_ANY_DATA_CONNECTION_STATE_CHANGED));
        
        getActivity().getContentResolver().registerContentObserver(Settings.Secure.getUriFor(Settings.Global.MOBILE_DATA),
                false, new ContentObserver(mHandler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.e(TAG, "xchanged to " + mConnService.getMobileDataEnabled());
                        mMobilePreference.setChecked(mConnService.getMobileDataEnabled());
                        super.onChange(selfChange);
                    }
            
        });
        mConnService =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

    }
    
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.e(SwitchPreference2.TAG, "onPreferenceTreeClick" , new Throwable());
        final boolean currentState = mConnService.getMobileDataEnabled();
        final boolean desiredState = !currentState;
        Log.e(TAG, "currentState = " + mConnService.getMobileDataEnabled() + ".." + currentState);
        mMobilePreference.setChecked(currentState);
        mConnService.setMobileDataEnabled(desiredState);
        mHandler.sendEmptyMessageDelayed(UPDATE_STATE, 1000);
        return true;
    }
    
    private void updateState() {
        mMobilePreference.setChecked(mConnService.getMobileDataEnabled());
    }
    
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.e(TAG, "changed to " + mConnService.getMobileDataEnabled());
            updateState();
        }
    };
    
}
