package net.juude.droidviews.widget.imageview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-5-14.
 */
public class FrescoViewFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fresco_view, container, false);
        SimpleDraweeView draweeSimple = (SimpleDraweeView) v.findViewById(R.id.drawee_simple);
        draweeSimple.setImageURI(Uri.parse("http://www.artisan.com.tw/images/blogs/DSC02163.JPG"));

        SimpleDraweeView draweeMore = (SimpleDraweeView) v.findViewById(R.id.drawee_more);
        draweeMore.setImageURI(Uri.parse("http://www.artisan.com.tw/images/blogs/DSC02163.JPG"));
        return v;
    }
}
