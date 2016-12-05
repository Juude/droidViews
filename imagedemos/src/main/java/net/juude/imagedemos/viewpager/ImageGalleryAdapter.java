package net.juude.imagedemos.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import top.perf.utils.collect.CollectUtils;

/**
 * Created by juude on 2016/12/5.
 */

public class ImageGalleryAdapter extends PagerAdapter implements View.OnClickListener {

    private ArrayList<String> mImages;
    private ItemClickListener mItemClickListener;

    public void bind(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    public String getImageAt(int position) {
        return mImages == null ? null : mImages.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public ItemClickListener getItemClickListener() {
        return mItemClickListener;
    }


    @Override
    public int getCount() {
        return CollectUtils.sizeOf(mImages);
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
        View v = getItemView(container, position);
        container.addView(v);
        return v;
    }

    public View getItemView(ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
        simpleDraweeView.setImageURI(mImages.get(position));
        simpleDraweeView.setTag(position);
        simpleDraweeView.setOnClickListener(this);
        return simpleDraweeView;
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(tag);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}