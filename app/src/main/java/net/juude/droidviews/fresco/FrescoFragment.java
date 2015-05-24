package net.juude.droidviews.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import net.juude.droidviews.R;

/**
 * Created by juude on 15/5/24.
 */
public class FrescoFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fresco, null);
        SimpleDraweeView circle_drawee = (SimpleDraweeView) v.findViewById(R.id.circle_drawee);
        circle_drawee.setImageURI(Uri.parse("https://41.media.tumblr.com/e5f73c5a527b5fe418f32273c548aa46/tumblr_noj02v533u1qgn23wo1_1280.jpg"));
        return v;
    }
}
