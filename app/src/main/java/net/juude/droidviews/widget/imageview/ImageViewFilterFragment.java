package net.juude.droidviews.widget.imageview;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.juude.droidviews.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by juude on 15/5/31.
 */
public class ImageViewFilterFragment extends Fragment{
    private int mCurrentMode = 0;
    private ImageView mMultiplyFilter;
    private TextView mType;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_view_filter, null);
        mMultiplyFilter = (ImageView) v.findViewById(R.id.filter_multiply);
        mMultiplyFilter.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        mType = (TextView)v.findViewById(R.id.type);
        return v;
    }

    @Override
    public void onResume() {
        Timer mTimer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentMode += 1;
                        mCurrentMode %= Modes.length;
                        mMultiplyFilter.setColorFilter(Color.RED, Modes[mCurrentMode]);
                        mType.setText("filter : " + Modes[mCurrentMode]);
                    }
                });
            }
        };
        mTimer.schedule(task, 0, 5000);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private static PorterDuff.Mode[] Modes = new PorterDuff.Mode[] {
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.OVERLAY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_IN

    };
}
