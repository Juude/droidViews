package net.juude.droidviews.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.juude.droidviews.R;

/**
 * Created by juude on 15/5/24.
 */
public class FrescoFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fresco, null);

        SimpleDraweeView draweeMore = (SimpleDraweeView) v.findViewById(R.id.drawee_more);
        draweeMore.setImageURI(Uri.parse("http://www.artisan.com.tw/images/blogs/DSC02163.JPG"));

        SimpleDraweeView circle_drawee = (SimpleDraweeView) v.findViewById(R.id.circle_drawee);
        circle_drawee.setImageURI(Uri.parse("https://41.media.tumblr.com/e5f73c5a527b5fe418f32273c548aa46/tumblr_noj02v533u1qgn23wo1_1280.jpg"));

        net.juude.droidviews.fresco.SimpleDraweeView draweeView = (net.juude.droidviews.fresco.SimpleDraweeView) v.findViewById(R.id.default_loading);
        draweeView.setImageURI(Uri.parse("https://40.media.tumblr.com/7589f12f781f7dc9013ed7506e1334e4/tumblr_notbiy66no1urqmrvo1_500.jpg"));
        draweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER);

        SimpleDraweeView scale_center = (SimpleDraweeView) v.findViewById(R.id.scale_center);
        scale_center.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));

        SimpleDraweeView scale_center_inside = (SimpleDraweeView) v.findViewById(R.id.scale_center_inside);
        scale_center_inside.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));

        SimpleDraweeView scale_center_crop = (SimpleDraweeView) v.findViewById(R.id.scale_center_crop);
        scale_center_crop.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
        scale_center_crop.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);

        SimpleDraweeView scale_fit_center = (SimpleDraweeView) v.findViewById(R.id.scale_fit_center);
        scale_fit_center.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
        return v;
    }
}
