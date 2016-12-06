package net.juude.droidviews.graphics;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-7-8.
 */
public class DrawableMultipleViewsFragment extends Fragment {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawable_two_views, null);
        GreenDrawable drawable = new GreenDrawable();
        View v1 = v.findViewById(R.id.view1);
        View v2 = v.findViewById(R.id.view2);
        v1.setBackground(drawable);
        v2.setBackground(drawable);
        return v;
    }

    private class GreenDrawable extends Drawable {

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.GREEN);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter cf) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }

}
