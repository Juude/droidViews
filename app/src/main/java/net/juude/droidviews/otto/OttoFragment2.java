package net.juude.droidviews.otto;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import net.juude.droidviews.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

/**
 * Created by juude on 15-7-9.
 */
@EFragment(R.layout.fragment_otto)
public class OttoFragment2 extends Fragment {
    @ViewById
    Button generate;

    @ViewById
    TextView time;

    @AfterViews
    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @AfterViews
    @Click(R.id.generate)
    public void onGenerating() {
        while(!isDetached()) {
            BusProvider.getInstance().post(produceLocationEvent());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
