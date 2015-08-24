package net.juude.droidviews.memory;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.juude.droidviews.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by juude on 15-8-4.
 */
public class MemoryFragment extends Fragment {
    private static final String TAG = "MemoryFragment";
    @Bind(R.id.triggerMemory)
    Button triggerMemory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memory, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.triggerMemory)
    public void onTriggerMemory() {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "uncaughtException", ex);
        }
    });

    ArrayList list = new ArrayList();
    for(int i = 1; ; i += 1) {
        byte[] bytes = new byte[(int) Math.pow(2, 20) * 2];
        list.add(bytes);
        Log.d(TAG, "i = " + i + " current memory : " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M max memory : " + ((Runtime.getRuntime().maxMemory()/1024/1024) + "M"));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    }

}
