package net.juude.widgetsdemos.switchx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.widgetsdemos.R;

/**
 * Created by sjd on 2017/7/12.
 */

public class SwitchxFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switchx, container, false);
        return view;
    }

}
