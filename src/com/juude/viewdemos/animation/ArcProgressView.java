
package com.juude.viewdemos.animation;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

;

public class ArcProgressView extends View implements ValueAnimator.AnimatorUpdateListener {

    public final static String TAG = "ArcProgressView";

    private Paint mPaintSlice = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRect = null;

    private TimeInterpolator mInterpolator = new CustomInterpolator();

    private ValueAnimator mAnimation;

    private float mUsedPercent;

    private float mOriginalPercent;

    private float mCurrentPercent;

    public ArcProgressView(Context context) {
        this(context, null);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setRect();
    }

    private void setRect()
    {
        int mRight = getRight();
        int mLeft = getLeft();
        int mBottom = getBottom();
        int mTop = getTop();

        int mPaddingLeft = getPaddingLeft();
        int mPaddingTop = getPaddingTop();
        int mPaddingRight = getPaddingRight();
        int mPaddingBottom = getPaddingLeft();
        int width = mRight - mLeft, height = mBottom - mTop;
        mRect = new RectF(mPaddingLeft + 1, mPaddingTop + 1, width - mPaddingRight - 1, height
                - mPaddingBottom - 1);

        SweepGradient sweepGradient = new SweepGradient(mRect.centerX(), mRect.centerY(),
                new int[] {
                        0x4000FF00, 0xFF00FF00, 0x0000FF00, 0x4000FF00
                }, new float[] {
                        0f, 0.74f, 0.75f, 0.99f
                });
        mPaintSlice.setShader(sweepGradient);

    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Resources res = getResources();
        float density = res.getDisplayMetrics().density;

        mPaintSlice.setDither(false);
        mPaintSlice.setColor(res.getColor(android.R.color.holo_blue_light));
        mPaintSlice.setStyle(Paint.Style.STROKE);
        mPaintSlice.setStrokeWidth(density * 5);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    public void updatePercent(float percent, boolean animate) {
        if (Float.compare(mUsedPercent, percent) == 0)
        {
            // return;
        }

        mOriginalPercent = mUsedPercent;
        mUsedPercent = percent;
        if (animate)
        {
            start();
        }
        else
        {
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(mRect, -90, mCurrentPercent * 360, false, mPaintSlice);
        ;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mCurrentPercent = (Float) animation.getAnimatedValue();
        invalidate();
    }

    private void createAnimation() {
        long duration = (long) (2000 * (mUsedPercent + mOriginalPercent));
        mAnimation = ValueAnimator.ofFloat(mOriginalPercent, 0, mUsedPercent);
        mAnimation.setDuration(duration);
        mAnimation.setInterpolator(mInterpolator);
        mAnimation.addUpdateListener(this);
    }

    public void start() {
        createAnimation();
        mAnimation.start();
    }

    public void end() {
        if (mAnimation != null) {
            mAnimation.end();
            mAnimation = null;
        }
    }

    private class CustomInterpolator extends LinearInterpolator
    { // TODO: make this useable
        @Override
        public float getInterpolation(float input) {
            /*
             * float result; float raito = mOriginalPercent / (mOriginalPercent
             * + mUsedPercent); result = input * input * input - 3 * raito *
             * input * input + 3 * raito * input;
             */
            return input;
        }
    }
}
