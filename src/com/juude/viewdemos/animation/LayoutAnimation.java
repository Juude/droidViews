
package com.juude.viewdemos.animation;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class LayoutAnimation extends FrameLayout {
    private int mPosition = 0;
    private int mDelayTime = 600;
    private int mAnimateTime = 4000;
    private ImageView mScanLine;
    private static final String TAG = "CaptureLayout";
    private LayoutParams mLayoutParams;
    private Rect mFrameRect = null;

    public LayoutAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // mScanLine = (ImageView)findViewById(R.id.scan_line);
        // mFrameRect = LewaUtils.getFrameRect(getContext());
        mLayoutParams = new LayoutParams(mFrameRect.right - mFrameRect.left,
                LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(mFrameRect.left, mFrameRect.top, 0, 0);
        mScanLine.setLayoutParams(mLayoutParams);
        createAnimation();
    }

    private void createAnimation() {
        ValueAnimator animator = ObjectAnimator.ofInt(mScanLine, "top", mFrameRect.top,
                mFrameRect.bottom);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(mAnimateTime + mDelayTime);
        // animator.setInterpolator(new LinearInterpolator());

        animator.setInterpolator(new TimeInterpolator() {

            @Override
            public float getInterpolation(float input) {
                float startPercent = (float) ((1.0 * mDelayTime / 2) / (mAnimateTime + mDelayTime));
                float endPercent = 1 - startPercent;
                if (input < startPercent)
                    return 0;
                else if (input > endPercent) {
                    return 1;
                }
                else
                {
                    return (input - startPercent) / (endPercent - startPercent);
                }
            }
        });

        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPosition = (Integer) animation.getAnimatedValue();
                mLayoutParams.setMargins(mFrameRect.left, mPosition, 0, 0);
                mScanLine.setLayoutParams(mLayoutParams);
                Log.e(TAG, "positon : " + mPosition);
            }
        });
        animator.start();
    }

}
