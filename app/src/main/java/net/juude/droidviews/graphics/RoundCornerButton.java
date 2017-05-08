package net.juude.droidviews.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import net.juude.droidviews.R;

import static android.R.attr.path;

/**
 * Created by juude on 2017/5/5.
 */

public class RoundCornerButton extends Button {

    public RoundCornerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerButton);
        int strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundCornerButton_strokeWidth, 1);
        int type = typedArray.getInt(R.styleable.RoundCornerButton_shape, 0);
        if (type == 0) {
            setBackground(new RoundCornerDrawable(strokeWidth));
        } else {
            setBackground(new VouchersDrawable(getContext(), strokeWidth));
        }
        typedArray.recycle();
    }

    public static class RoundCornerDrawable extends Drawable {

        private final float mStrokeWidth;
        private RectF mRect = new RectF();
        Paint mPaint = new Paint();

        public RoundCornerDrawable(int strokeWidth) {
            mStrokeWidth = strokeWidth;
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
            mRect.set(bounds.left + mStrokeWidth / 2,
                      bounds.top + mStrokeWidth / 2,
                      bounds.right - mStrokeWidth / 2,
                      bounds.bottom - mStrokeWidth / 2
                    );
            Path path = new Path();
            path.addRoundRect(mRect, 10, 10, Path.Direction.CCW);
            canvas.drawRoundRect(mRect, 10, 10, mPaint);
            //canvas.drawPath(path, mPaint);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }

    public static class VouchersDrawable extends Drawable {

        private Path mPath;
        private Paint mPaint;
        private int mStrokeColor;
        private RectF mLeftRectF = new RectF();
        private RectF mRightRectF = new RectF();
        private RectF mDestRect = new RectF();
        private float mStrokeWidth;
        //72 28 5.25
        public VouchersDrawable(Context context, int strokeWidth) {
            context = context.getApplicationContext();
            mPaint = new Paint();
            mStrokeColor = Color.RED;
            mPaint.setColor(mStrokeColor);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mStrokeWidth = strokeWidth;
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setAntiAlias(true);
            mPath = new Path();
        }

        @Override
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
//            mDestRect.set(
//                    (float) Math.ceil(bounds.left + mStrokeWidth / 2),
//                    (float) Math.ceil(bounds.top + mStrokeWidth / 2),
//                    (float) Math.floor(bounds.right - mStrokeWidth / 2),
//                    (float) Math.floor(bounds.bottom - mStrokeWidth / 2)
//            );
            mDestRect.set(
                    bounds.left + mStrokeWidth / 2,
                    bounds.top + mStrokeWidth / 2,
                    bounds.right - mStrokeWidth / 2,
                    bounds.bottom - mStrokeWidth / 2
            );
            mPath.reset();
            mPath.moveTo(0, 0);
            float w = mDestRect.width();
            float h = mDestRect.height();
            mPath.lineTo(0, 0.4f * h);
            mLeftRectF.set(-0.1f * h, 0.4f * h, 0.1f * h, 0.6f * h);
            //度数从
            mPath.addArc(mLeftRectF, -90, 180);
            mPath.lineTo(0, 0.6f * h);
            mPath.lineTo(0, h);
            mPath.lineTo(w, h);
            mPath.lineTo(w, 0.6f * h);
            mRightRectF.set(w - 0.1f * h, 0.4f * h, w + 0.1f * h, 0.6f * h);
            mPath.arcTo(mRightRectF, 90, 180);
            mPath.lineTo(w, 0);
            mPath.lineTo(0, 0);
            mPath.close();
            mPath.offset(mDestRect.left, mDestRect.top);
            mPath.computeBounds(mDestRect, true);

            Path path = new Path();
            path.addRoundRect(mDestRect, 10, 10, Path.Direction.CCW);
            canvas.drawPath(path, mPaint);
//            canvas.drawRoundRect(mDestRect, 10, 10, mPaint);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }


}
