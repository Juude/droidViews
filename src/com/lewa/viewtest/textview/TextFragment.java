
package com.lewa.viewtest.textview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lewa.viewtest.LayoutActivity;
import com.lewa.viewtest.R;
import com.lewa.viewtest.Utils;

public class TextFragment extends Fragment {
    private static final String TAG = "LinkedTextFragment";
    private Activity mActivity;
    private final static boolean DEBUG = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
        com.lewa.viewtest.Utils.addButton(this, layout, "lined_text_fragment", "startLinkedFragment");
        Utils.addButton(this, layout, "measureText", "measureText");
        Button button = new Button(mActivity);
        button.setText("show me");
        Drawable completeRes = getResources().getDrawable(R.drawable.ic_launcher);
        completeRes.setBounds(0, 0, completeRes.getMinimumWidth(), completeRes.getMinimumHeight());
        button.setCompoundDrawables(completeRes, null, null, null);
        layout.addView(button, 600, LayoutParams.WRAP_CONTENT);
        return layout;
    }

    public void startLinkedFragment() {
        Intent i = new Intent(mActivity, LayoutActivity.class);
        i.putExtra(LayoutActivity.KEY_LAYOUT, R.layout.lined_text_fragment);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (DEBUG) {
            Log.e(TAG, "KEY_LAYOUT.." + LayoutActivity.KEY_LAYOUT);
        }
        mActivity.startActivity(i);
    }
}
