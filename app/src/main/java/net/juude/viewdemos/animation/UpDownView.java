
package net.juude.viewdemos.animation;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;
import android.view.animation.LinearInterpolator;

import net.juude.viewdemos.R;

public class UpDownView extends View {

    private Rect mRect;
    private Paint mPaint;
    private int mPosition = 0;
    private int mDelayTime = 300;
    private int mAnimateTime = 2000;
    private NinePatchDrawable mScanLine;

    public UpDownView(Context context) {

        super(context);
        mRect = new Rect();
        mPaint = new Paint();
        Bitmap scanLine = BitmapFactory.decodeResource(getResources(), R.drawable.scan_line);
        mScanLine = new NinePatchDrawable(getResources(), scanLine, scanLine.getNinePatchChunk(),
                null, null);

        ValueAnimator animator = ValueAnimator.ofInt(0,
                getResources().getDisplayMetrics().heightPixels - 200);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(mAnimateTime + mDelayTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPosition = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.set(0, mPosition, getWidth(), mPosition + 5);
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRect, mPaint);

        mScanLine.setBounds(mRect);
        mScanLine.draw(canvas);
        super.onDraw(canvas);
    }
}
