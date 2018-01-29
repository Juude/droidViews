package net.juude.droidviews.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sjd on 2018/1/2.
 */

public class VerticalDashView extends View {


    private Paint mPaint;
    private Path mPath;

    public VerticalDashView(Context context) {
        this(context, null);
    }

    public VerticalDashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new DashPathEffect(new float[]{8, 10, 8, 10}, 0));
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(getWidth() /2, 0);
        mPath.lineTo(getWidth() /2, getHeight());
        mPaint.setStrokeWidth(getWidth());
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }
}
