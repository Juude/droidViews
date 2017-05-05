package net.juude.droidviews.perf.memory;


import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.juude.droidviews.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by juude on 15-8-4.
 * this class demonstrate how to create memory leak.
 * press triggerMemory button to trigger memory leak.
 */
public class MemoryFragment extends Fragment {

    private static final String TAG = "MemoryFragment";
    @Bind(R.id.triggerMemory)
    Button triggerMemory;

    @Bind(R.id.text_info)
    TextView textInfo;

    @Bind(R.id.text_error)
    TextView textError;

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
        textError.setText("s");
        new Thread(() -> {
            Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
                textError.postDelayed(() -> textError.setText(Log.getStackTraceString(ex)), 0);
                Log.e(TAG, "uncaughtException", ex);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, "tag", new AlarmManager.OnAlarmListener() {
                    @Override
                    public void onAlarm() {
                        Toast.makeText(getContext().getApplicationContext(), "uncaughtException" + Log.getStackTraceString(ex), Toast.LENGTH_LONG).show();
                    }
                }, new Handler(Looper.getMainLooper()));
            });

            ArrayList list = new ArrayList();
            for(int i = 1; ; i += 1) {
                byte[] bytes = new byte[(int) Math.pow(2, 20) * 2];
                list.add(bytes);
                String info = "i = " + i + " current memory : " + (Runtime.getRuntime().totalMemory()/1024/1024) + "M max memory : " + ((Runtime.getRuntime().maxMemory()/1024/1024) + "M");
                Log.d(TAG, info);
                textInfo.post(() -> textInfo.setText(info));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
