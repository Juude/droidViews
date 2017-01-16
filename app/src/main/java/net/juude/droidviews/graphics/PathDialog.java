package net.juude.droidviews.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import static android.graphics.Path.FillType.WINDING;

/**
 * Created by juude on 2016/12/17.
 */

public class PathDialog extends FrameLayout {

    private Path mPath;

    int triangleHeight = 50;
    int triangleWidth = 60;
    int trianglePosition = 100;
    private Paint mPaint;

    public PathDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        //setBackgroundColor(Color.RED);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
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
        mPath.reset();
        //mPath.setFillType(WINDING);
        mPath.moveTo(0, triangleHeight);
        mPath.lineTo(trianglePosition - triangleWidth / 2, triangleHeight);
        mPath.lineTo(trianglePosition, 0);
        mPath.lineTo(trianglePosition + triangleWidth /2, triangleHeight);
        mPath.lineTo(w, triangleHeight);
        mPath.lineTo(w, h);
        mPath.lineTo(0, h);
        mPath.close();
        invalidate();
    }
}
