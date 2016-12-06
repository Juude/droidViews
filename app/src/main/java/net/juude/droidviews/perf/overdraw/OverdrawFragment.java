package net.juude.droidviews.perf.overdraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import net.juude.droidviews.R;

/**
 * Created by sjd on 2016/12/4.
 * //this class demonstrate overdraw, open overdraw settings to show overdraw info.
 */

public class OverdrawFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_overdraw, null, false);
        return v;
    }

}
