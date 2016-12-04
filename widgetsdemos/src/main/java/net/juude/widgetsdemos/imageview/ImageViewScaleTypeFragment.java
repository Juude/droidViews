package net.juude.widgetsdemos.imageview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.widgetsdemos.R;


/**
 * Created by juude on 15-5-14.
 */
public class ImageViewScaleTypeFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_scaletype_view, container, false);
        return v;
    }
}