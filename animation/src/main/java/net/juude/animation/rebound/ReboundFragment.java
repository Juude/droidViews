package net.juude.animation.rebound;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import net.juude.animation.R;

/**
 * Created by juude on 15-7-8.
 */
public class ReboundFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rebound, null);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) v.findViewById(R.id.rebound_image);
        simpleDraweeView.setImageURI(Uri.parse("http://www.keenthemes.com/preview/metronic/theme/assets/global/plugins/jcrop/demos/demo_files/image1.jpg"));
        //simpleDraweeView.setOnClickListener(v1 -> rebound(v1));
        return v;
    }

    private void rebound(final View v) {
        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = value;
                v.setScaleX(scale);
                v.setScaleY(scale);
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);
    }
}
