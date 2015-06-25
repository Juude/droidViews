package net.juude.droidviews.jni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-6-25.
 */
public class JniFragment extends Fragment{
    static {
        System.loadLibrary("jni");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_jni, null);
        Button button = (Button) v.findViewById(R.id.child_fork);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                forkChildProcess();
            }
        });
        return v;
    }

    public static native void forkChildProcess();
}
