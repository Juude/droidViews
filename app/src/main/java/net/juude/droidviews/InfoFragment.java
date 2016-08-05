package net.juude.droidviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by juude on 15-7-3.
 */
public class InfoFragment extends Fragment{
    private static final String TAG = "InfoFragment";
    private TextView mTextInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, null);
        mTextInfo = (TextView) v.findViewById(R.id.text_info);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        StringBuilder sb = new StringBuilder();

        Log.d(TAG, " yo : " + sb.toString());
        mTextInfo.setText(sb.toString());
        try {
            logAndAppend(TAG, "View " + Class.forName("android.view.View"));
            logAndAppend(TAG, "ActiveServices" + Class.forName("com.android.server.ActiveServices"));
            logAndAppend(TAG, "MiuiInitServer " + Class.forName("com.miui.server.MiuiInitServer"));
            logAndAppend(TAG, "AlphabetFastIndexer " + Class.forName("miui.widget.AlphabetFastIndexer"));
            logAndAppend(TAG, "View " + Class.forName("android.view.View"));

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "", e);
        }
    }

    private void logAndAppend(String tag, String info) {
        mTextInfo.setText(mTextInfo.getText() + info + "\n");
        Log.d(tag, info);
    }
}
