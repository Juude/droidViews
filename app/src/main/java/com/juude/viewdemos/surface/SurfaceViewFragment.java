
package com.juude.viewdemos.surface;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

public class SurfaceViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
        layout.addView(new StarFlagView(getActivity()), LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        return layout;
    }
}
