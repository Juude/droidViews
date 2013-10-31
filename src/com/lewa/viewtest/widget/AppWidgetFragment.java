package com.lewa.viewtest.widget;

import android.app.Fragment;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lewa.viewtest.R;


public class AppWidgetFragment extends Fragment {
    private AppWidgetHost mAppWidgetHost;
    private static final String TAG = "AppWidgetFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup)inflater.inflate(R.layout.container, null);
        addNetUsage(group);
        return group;
    }
    
    private void addNetUsage(ViewGroup parent){
        final Context context = getActivity();
        ComponentName netmgr = new ComponentName("com.lewa.netmgr",
                "com.lewa.netmgr.IndicatorProvider");
        Intent netmgrIntent = new Intent();
        netmgrIntent.setComponent(netmgr);
        if(context.getPackageManager().queryBroadcastReceivers(netmgrIntent, 0).size() > 0){
            parent.removeAllViews();
            parent.setClickable(false);
            parent.setFocusable(false);
            parent.setOnClickListener(null);
            
            try {
                mAppWidgetHost = new AppWidgetHost(context, 1024);
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
                Log.e(TAG, "", e);
            }
        }
    }

    @Override
    public void onStart() {
        mAppWidgetHost.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        mAppWidgetHost.stopListening();
        super.onStop();
    }
    
    
    
}
