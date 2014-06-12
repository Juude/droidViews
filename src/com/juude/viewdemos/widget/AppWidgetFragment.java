package com.juude.viewdemos.widget;

import com.juude.viewdemos.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AppWidgetFragment extends Fragment {

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup)inflater.inflate(R.layout.container, null);
        View net = group.findViewById(R.id.data_monitor_layout);
        NetUsageHelper.init(net, net);
        NetUsageHelper.showNetUsage();
        //addNetUsage(group);
        return group;
    }
    
}
