package net.juude.widgetsdemos.viewpager;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.widgetsdemos.R;

import java.util.ArrayList;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by juude on 2016/12/5.
 */

public class ImageGalleryDelegate {

    private static final String TAG = "ImageGalleryDelegate";
    private ImageGalleryAdapter mSmallImageAdapter;
    private ViewPager mFullscreenImageGallery;
    private ImageGalleryAdapter mFullscreenImageAdapter;
    private ViewPager mSmallViewPager;
    private Context mContext;
    private ArrayList<String> mImages;

    public void bind(ArrayList<String> images) {
        mImages = images;
        mSmallImageAdapter.bind(images);
        mSmallImageAdapter.notifyDataSetChanged();
        if (mFullscreenImageAdapter != null) {
            mFullscreenImageAdapter.bind(images);
            mFullscreenImageAdapter.notifyDataSetChanged();
        }
    }

    public ImageGalleryDelegate(ViewPager smallImageGallery) {
        mSmallViewPager = smallImageGallery;
        mContext = mSmallViewPager.getContext();
        mSmallImageAdapter = new ImageGalleryAdapter();
        mSmallImageAdapter.setItemClickListener(new ImageGalleryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                addOverLayImage(position);
            }
        });
        mSmallViewPager.setAdapter(mSmallImageAdapter);
        mSmallViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected : " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ViewGroup getDectorView() {
        return (ViewGroup) ((Activity)mContext).getWindow().getDecorView();
    }

    private void toggleVisibilityExcludeFullscreenView(boolean visible) {
        ViewGroup dectorView = getDectorView();
        for (int i = 0; i < getDectorView().getChildCount(); i++) {
            View child = dectorView.getChildAt(i);
            if (child != mFullscreenImageGallery) {
                child.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void addOverLayImage(final int position) {
        String url = mSmallImageAdapter.getImageAt(position);
        if (url != null) {
            if (mFullscreenImageGallery == null) {
                mFullscreenImageGallery = new ViewPager(mContext);
            }
            if (mFullscreenImageAdapter == null) {
                mFullscreenImageAdapter = new FullscreenImageAdapter();
                mFullscreenImageAdapter.setItemClickListener(new ImageGalleryAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        getDectorView().removeView(mFullscreenImageGallery);
                    }
                });
                mFullscreenImageGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mSmallViewPager.setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
            mFullscreenImageGallery.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                    toggleVisibilityExcludeFullscreenView(false);
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    toggleVisibilityExcludeFullscreenView(true);
                }
            });
            mFullscreenImageAdapter.bind(mImages);
            mFullscreenImageGallery.setAdapter(mFullscreenImageAdapter);
            mFullscreenImageGallery.setCurrentItem(position);
            getDectorView().addView(mFullscreenImageGallery,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private class FullscreenImageAdapter extends ImageGalleryAdapter implements OnPhotoTapListener {
        @Override
        public View getItemView(ViewGroup container, final int position) {
            View v = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_fullscreen_image, container, false);
            PhotoDraweeView photoDraweeView = (PhotoDraweeView) v.findViewById(R.id.photo_view);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            final String url = mImages.get(position);
            if (url != null) {
                photoDraweeView.setPhotoUri(Uri.parse(url));
            }
            photoDraweeView.setTag(position);
            photoDraweeView.setOnPhotoTapListener(this);
            return v;
        }

        @Override
        public void onPhotoTap(View view, float x, float y) {
            if (getItemClickListener() != null) {
                int tag = (int) view.getTag();
                getItemClickListener().onItemClick(tag);
            }
        }
    }
}
