package net.juude.imagedemos.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by juude on 2016/12/5.
 *
 * layout: image_gallery
 */

public class ImageGallery extends ViewPager{

    private final ImagePageAdapter mImagePageAdapter;

    public ImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImagePageAdapter = new ImagePageAdapter();
        setAdapter(mImagePageAdapter);
    }

    public void bind(ArrayList<String> images) {
        mImagePageAdapter.bind(images);
    }

    static class ImagePageAdapter extends PagerAdapter {

        public void bind(ArrayList<String> images) {
            mImages = images;
            notifyDataSetChanged();
        }

        private ArrayList<String> mImages;

        @Override
        public int getCount() {
            return Collection mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
            simpleDraweeView.setImageURI(mImages.get(position));
            container.addView(simpleDraweeView);
            return simpleDraweeView;
        }
    }
}
