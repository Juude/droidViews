package net.juude.widgetsdemos.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.widgetsdemos.R;


/**
 * this class demonstrate usage of LinearLayout include
 *  1. dividers
 * */
public class LinearLayoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear_layout, null);
        return v;
    }
    
}
