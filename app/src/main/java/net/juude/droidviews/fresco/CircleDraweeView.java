package net.juude.droidviews.fresco;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by juude on 15/5/24.
 */
public class CircleDraweeView extends SimpleDraweeView{

    private static final String TAG = "ProgressiveDraweeView";

    private float mProgress;
    private Paint mPaint;

    public CircleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw canvas width : " + canvas.getWidth() + " height : " + canvas.getHeight());
        canvas.drawArc(0, 0, canvas.getWidth(), canvas.getHeight(), -90, 135, true, mPaint);
        super.onDraw(canvas);
        //canvas.drawArc();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG, "draw canvas width : " + canvas.getWidth() + " height : " + canvas.getHeight());
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }
}
