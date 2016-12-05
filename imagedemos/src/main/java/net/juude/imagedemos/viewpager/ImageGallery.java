package net.juude.imagedemos.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by juude on 2016/12/5.
 *
 * layout: image_gallery
 */

public class ImageGallery extends ViewPager{


    public ImageGallery(Context context) {
        this(context, null);
    }

    public ImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
