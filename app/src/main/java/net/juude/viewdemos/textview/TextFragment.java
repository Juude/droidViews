
package net.juude.viewdemos.textview;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import net.juude.viewdemos.R;
import net.juude.viewdemos.Utils;

public class TextFragment extends Fragment {
    public static final String TAG = "LinkedTextFragment";
    private Activity mActivity;
    public final static boolean DEBUG = true;

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
        Utils.addButton(this, layout, "lined_text_fragment", "startLinkedFragment");
        Utils.addButton(this, layout, "measureText", "measureText");
        Button button = new Button(mActivity);
        button.setText("show me");
        Drawable completeRes = getResources().getDrawable(R.drawable.ic_launcher);
        completeRes.setBounds(0, 0, completeRes.getMinimumWidth(), completeRes.getMinimumHeight());
        button.setCompoundDrawables(completeRes, null, null, null);
        layout.addView(button, 600, LayoutParams.WRAP_CONTENT);
        return layout;
    }

}
