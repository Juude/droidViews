package net.juude.droidviews.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-7-10.
 */
public class GlideFragment extends Fragment{
    private ImageView mGlideImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glide, null);
        mGlideImage = (ImageView) v.findViewById(R.id.glide_image_url);
        Glide.with(getActivity())
                .load("https://raw.githubusercontent.com/JuudeDemos/WebDisk/master/food/IMG_0693.PNG")
                .fitCenter()
                .placeholder(R.drawable.ic_action_grade)
                .into(mGlideImage);

        ImageView mGlideImageRes = (ImageView) v.findViewById(R.id.glide_image_res);
        Glide.with(getActivity())
                .load("android.resource://net.juude.droidviews/drawable/juude")
                .fitCenter()
                .placeholder(R.drawable.ic_action_grade)
                .into(mGlideImageRes);

        ImageView mGlideImageAssets = (ImageView) v.findViewById(R.id.glide_image_assets);
        Glide.with(getActivity())
                .load("android_asset://download.png")
                .fitCenter()
                .placeholder(R.drawable.ic_action_grade)
                .into(mGlideImageAssets);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
