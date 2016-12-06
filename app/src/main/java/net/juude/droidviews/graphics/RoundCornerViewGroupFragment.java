package net.juude.droidviews.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by juude on 15/5/23.
 */
public class RoundCornerViewGroupFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RoundCornerViewGroup roundGroup = new RoundCornerViewGroup(getActivity());
        View child = new View(getActivity());
        child.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );
        roundGroup.addView(child);
        return roundGroup;
    }

    public static class RoundCornerViewGroup extends FrameLayout {
        private Path mPath = new Path();
        private int mCornerRadius = 50;

        public RoundCornerViewGroup(Context context) {
            super(context);
            setWillNotDraw(false);
            setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.clipPath(mPath);
            super.draw(canvas);
            canvas.restore();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            RectF r = new RectF(0, 0, w, h);
            mPath = new Path();
            mPath.addRoundRect(r, mCornerRadius, mCornerRadius, Path.Direction.CW);
            mPath.close();
        }

        public void setCornerRadius(int radius) {
            mCornerRadius = radius;
            invalidate();
        }
    }
}
