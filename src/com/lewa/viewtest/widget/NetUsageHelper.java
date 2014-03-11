package com.lewa.viewtest.widget;

import android.app.ActivityManagerNative;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo; 
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import lewa.provider.ExtraSettings;
import android.util.Log;

public class NetUsageHelper {
    private static final int NET_SPEED_INTERVAL_SEC = 2;
    private static final int NET_SPEED_INTERVAL = NET_SPEED_INTERVAL_SEC * 1000;
    private static final int NET_USAGE_INTERVAL = 5 * 1000;
    private static final int APPWIDGET_HOST_ID = 1024;
    public static final long KB_IN_BYTES = 1024;
    public static final long MB_IN_BYTES = KB_IN_BYTES * 1024;
    public static final long GB_IN_BYTES = MB_IN_BYTES * 1024;
    public static final long TB_IN_BYTES = GB_IN_BYTES * 1024;
    public static final String KEY_STATUS_BAR_NET_USAGE = "status_bar_net_usage";
    public static final String KEY_STATUS_BAR_NET_SPEED = ExtraSettings.System.STATUS_BAR_NET_SPEED;
    private AppWidgetHost mAppWidgetHost;
    private ViewGroup mDataUsageLayout;
    private boolean mHasNetMgr = false;
    private boolean mShowNetUsage, mShowNetSpeed;
    private Context mContext;
    private long mRx = 0L;
    private final TextView mNetSpeed;
    private Handler mHandler = new Handler();
    private Intent mUpdateIntent = new Intent("com.lewa.netmgr.APPWIDGET_UPDATE");
    private static NetUsageHelper sHelper;
    private boolean mConnectivity = false;
    private Timer mTimer;
    public static final String TAG = "NetUsageHelper";
    public static boolean DEBUG = true;

    public static boolean init(View statusBar, View expand){
        sHelper = new NetUsageHelper(statusBar, expand);
        if(DEBUG) {
            Log.e(TAG, "hasNetMgr : " + sHelper.mHasNetMgr);
        }
        return sHelper.mHasNetMgr;
    }
    
    private void updateSettings() {
        ContentResolver resolver = mContext.getContentResolver();
        mShowNetSpeed = (Settings.System.getInt(resolver,
        KEY_STATUS_BAR_NET_SPEED, 0) == 1);
        mShowNetUsage = (Settings.System.getInt(resolver,
        KEY_STATUS_BAR_NET_USAGE, 1) == 1);
    }

    private NetUsageHelper(View statusBar, View expand) {
        mContext = statusBar.getContext();
        mNetSpeed = new TextView(mContext);
        mDataUsageLayout = (ViewGroup)expand;
        addNetUsage(mDataUsageLayout);
        (new SettingsObserver(mHandler)).observe();
        new DataConnectionReciver(mContext);
        updateSettings();
    }
    
    private Runnable mNetSpeedTasks = new Runnable() {
        @Override
        public void run() {
            long rx = TrafficStats.getTotalRxBytes();
            mNetSpeed.setText(formatShorterSize(rx <= mRx ? 0 : (rx - mRx) / NET_SPEED_INTERVAL_SEC, "%1.2f"));
            mRx = rx;
            if(mShowNetSpeed)
                mHandler.postDelayed(mNetSpeedTasks, NET_SPEED_INTERVAL);
        }
    };
    
    private class DataConnectionReciver extends BroadcastReceiver {
        public DataConnectionReciver(Context context){
            context.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            context.registerReceiver(this, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                mConnectivity = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if(mShowNetSpeed && mNetSpeed != null){
                    mNetSpeed.setVisibility(mConnectivity ? View.VISIBLE : View.GONE);
                }
            }
            else if(Intent.ACTION_CONFIGURATION_CHANGED.equals(intent.getAction())) {
                if(mShowNetUsage && mDataUsageLayout != null) {
                    final boolean landscape = mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                    mDataUsageLayout.setVisibility(landscape ? View.GONE : View.VISIBLE);
                }
            }
        }
    }
    
    public static String formatShorterSize(long size, String format){
        String str;
        if( size < KB_IN_BYTES)
            str = size + "B/s";
        else if( size < MB_IN_BYTES)
            str = String.format(format, (float)size / KB_IN_BYTES) + "K/s";
        else if( size < GB_IN_BYTES)
            str = String.format(format, (float)size / MB_IN_BYTES) + "M/s";
        else if( size < TB_IN_BYTES)
            str = String.format(format, (float)size / GB_IN_BYTES) + "G/s";
        else
            str = String.format(format, (float)size / TB_IN_BYTES) + "T/s";
        return str;
    }
    
    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }
        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    KEY_STATUS_BAR_NET_SPEED), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    KEY_STATUS_BAR_NET_USAGE), false, this);
            onChange(false);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateSettings();
            if(mNetSpeed != null){
                if(mShowNetSpeed){
                    setNetSpeedVisibility(isNetworkConnected(mContext));
                    mHandler.postDelayed(mNetSpeedTasks, NET_SPEED_INTERVAL);
                } else {
                    setNetSpeedVisibility(false);
                }
            }
            if(mDataUsageLayout != null)
                mDataUsageLayout.setVisibility(mShowNetUsage ? View.VISIBLE : View.GONE);
        }
        
        private void setNetSpeedVisibility(boolean visible){
            mNetSpeed.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
    private static boolean isNetworkConnected(Context context){
        try {
            return ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    
    private void addNetUsage(ViewGroup parent){
        final Context context = mContext;
        ComponentName netmgr = new ComponentName("com.lewa.netmgr",
                "com.lewa.netmgr.IndicatorProvider");
        Intent netmgrIntent = new Intent();
        netmgrIntent.setComponent(netmgr);
        if(mHasNetMgr = context.getPackageManager().queryBroadcastReceivers(netmgrIntent, 0).size() > 0){
            Log.d(TAG, "1");
            parent.removeAllViews();
            parent.setClickable(false);
            parent.setFocusable(false);
            parent.setOnClickListener(null);
            
            try {
                Log.d(TAG, "2");
                mAppWidgetHost = new LAppWidgetHost(context, APPWIDGET_HOST_ID);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
                manager.bindAppWidgetId(appWidgetId, netmgr);
                AppWidgetProviderInfo providerInfo = manager.getAppWidgetInfo(appWidgetId);
                AppWidgetHostView widget = mAppWidgetHost.createView(context, appWidgetId, providerInfo);
                widget.setAppWidget(appWidgetId, providerInfo);
                widget.setClickable(false);
                widget.setPadding(0,0,0,0);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                parent.addView(widget, params);
            } catch (Exception e) {
            }
        }
    }

    private void showAppWidgetHost() {
        if(DEBUG)Log.d(TAG, "showAppWidgetHost");
        if(mHasNetMgr && ActivityManagerNative.isSystemReady()){
            Log.d(TAG, "3");
            try {
                mAppWidgetHost.startListening();
                mContext.sendBroadcast(mUpdateIntent);
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mContext.sendBroadcast(mUpdateIntent);
                    }
                }, NET_USAGE_INTERVAL, NET_USAGE_INTERVAL);
                if(mShowNetUsage && mDataUsageLayout != null && mContext.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
                    mDataUsageLayout.setVisibility(View.VISIBLE);
            } catch (RuntimeException ex){}
        }
        if(mShowNetSpeed){
            mNetSpeed.setVisibility(View.GONE);
        }
    }

    private void hideAppWidgetHost() {
        if(DEBUG)Log.d(TAG, "hideAppWidgetHost");
        if(mHasNetMgr){
            mAppWidgetHost.stopListening();
            if(mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            if(mShowNetUsage && mDataUsageLayout != null)
                mDataUsageLayout.setVisibility(View.GONE);
        }
        if(mShowNetSpeed && mConnectivity){
            mNetSpeed.setVisibility(View.VISIBLE);
        }
    }
    public static void showNetUsage(){
        sHelper.showAppWidgetHost();
    }
    public static void hideNetUsage(){
        sHelper.hideAppWidgetHost();
    }

    class LAppWidgetHost extends AppWidgetHost {

        public LAppWidgetHost(Context context, int hostId) {
            super(context, hostId);
        }

        @Override
        protected void onProviderChanged(int appWidgetId, AppWidgetProviderInfo appWidget) {
            super.onProviderChanged(appWidgetId, appWidget);
            Log.e(TAG, "onProviderChanged");
        }

        @Override
        protected AppWidgetHostView onCreateView(Context context, int appWidgetId,
                AppWidgetProviderInfo appWidget) {
            Log.e(TAG, "onCreateView");
            AppWidgetHostView v = super.onCreateView(context, appWidgetId, appWidget);
            return v;
        }
    } 
}
