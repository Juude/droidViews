package net.juude.droidviews.otto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import net.juude.droidviews.R;

import java.util.Date;

/**
 * Created by juude on 15-7-9.
 */
public class OttoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_otto, null);
        generate = (Button) v.findViewById(R.id.generate);
        time = (TextView) v.findViewById(R.id.time);
        generate.setOnClickListener(v1 -> onGenerating());
        return v;
    }


    Button generate;

    TextView time;

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    public void onGenerating() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isDetached()) {
                    getActivity().runOnUiThread(() -> BusProvider.getInstance().post(produceLocationEvent()));

                    System.out.println("hehhe" + produceLocationEvent());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Produce public String produceLocationEvent() {
        // Provide an initial value for location based on the last known position.
        return new String(String.valueOf(new Date(System.currentTimeMillis())));
    }

    @Subscribe
    public void receiveResults(String string){
        time.setText(string);
    }
}
