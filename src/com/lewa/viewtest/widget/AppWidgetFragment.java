package com.lewa.viewtest.widget;

import android.app.Fragment;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lewa.viewtest.R;


public class AppWidgetFragment extends Fragment {
    
    private AppWidgetHost mAppWidgetHost;
    private static final String TAG = "AppWidgetFragment";
    private View mView;
    private View mWidget;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            if(intent.getStringExtra("view") == null) {
                setTextColor(mView);
            }
            else {
                setTextColor(mWidget);
            }
        }
        
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup)inflater.inflate(R.layout.container, null);
        addNetUsage(group);
        mView = group;
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
                mAppWidgetHost = new LAppWidgetHost(context, 1024);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
                manager.bindAppWidgetId(appWidgetId, netmgr);
                AppWidgetProviderInfo providerInfo = manager.getAppWidgetInfo(appWidgetId);
                AppWidgetHostView widget = mAppWidgetHost.createView(context, appWidgetId, providerInfo);
                widget.setAppWidget(appWidgetId, providerInfo);
                widget.setClickable(false);
                widget.setPadding(0,0,0,0);
                setTextColor(widget);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                parent.addView(widget, params);
                setTextColor(widget);
                mWidget = widget;
                mWidget.isActivated();
            } catch (Exception e) {
                Log.e(TAG, "", e);
            }
        }
    }
    
    public void setTextColor(View view) {
        if(view instanceof TextView) {
            Log.e(TAG, "setTextColor  n");
            ((TextView)view).setTextColor(Color.RED);
        }
        else if(view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for(int i = 0 ; i < viewGroup.getChildCount(); i++) {
                setTextColor(viewGroup.getChildAt(i));
            }
        }
    }

    @Override
    public void onStart() {
        getActivity().registerReceiver(mReceiver, new IntentFilter("com.lewa.intent.jdsong"));
        mAppWidgetHost.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mReceiver);
        mAppWidgetHost.stopListening();
        super.onStop();
    }
    
    class LAppWidgetHost extends AppWidgetHost {

        public LAppWidgetHost(Context context, int hostId) {
            super(context, hostId);
        }

        @Override
        protected void onProviderChanged(int appWidgetId, AppWidgetProviderInfo appWidget) {
            super.onProviderChanged(appWidgetId, appWidget);
            setTextColor(mWidget);
        }

        @Override
        protected AppWidgetHostView onCreateView(Context context, int appWidgetId,
                AppWidgetProviderInfo appWidget) {
            AppWidgetHostView v = super.onCreateView(context, appWidgetId, appWidget);
            setTextColor(v);
            return v;
        }
        
        
 
    } 
    
}
