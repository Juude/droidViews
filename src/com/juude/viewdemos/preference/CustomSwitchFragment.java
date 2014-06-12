package com.juude.viewdemos.preference;

import com.juude.viewdemos.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomSwitchFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_custom_preference, null);
        LinearLayout mainContent = (LinearLayout) v.findViewById(R.id.mainContent);
        View result = CustomSwitchPreference.inflateCheckBoxPreference(inflater, getActivity());
        mainContent.addView(result);
        return v;
    }
}
