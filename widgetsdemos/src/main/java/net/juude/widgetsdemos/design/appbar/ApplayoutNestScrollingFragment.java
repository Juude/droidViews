package net.juude.widgetsdemos.design.appbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import net.juude.widgetsdemos.R;


/**
 * Created by juude on 2016/11/16.
 */
public class ApplayoutNestScrollingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_appbar_paralell, container, false);
//        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        return v;
    }

}