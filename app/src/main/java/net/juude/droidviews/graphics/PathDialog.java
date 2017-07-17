package net.juude.droidviews.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import static android.graphics.Path.FillType.EVEN_ODD;
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
    private Path mDestPath;
    private RectF mTempRectF = new RectF();
    private RectF mDestRectF = new RectF();

    public PathDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        setWillNotDraw(false);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mDestPath = new Path();
        setBackgroundColor(Color.GRAY);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);

        mPath.reset();
        Matrix matrix = new Matrix();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        matrix.setScale((width - 60f)/width, (height - 60f)/height);
        mDestPath.transform(matrix, mPath);
        mPath.computeBounds(mTempRectF, true);
        mDestPath.computeBounds(mDestRectF, true);
        mPath.offset(mDestRectF.centerX() - mTempRectF.centerX(), mDestRectF.centerY() - mTempRectF.centerY());
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawPath(mDestPath, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        mPath.setFillType(EVEN_ODD);
        mPath.moveTo(20, triangleHeight + 20);
        mPath.lineTo(trianglePosition - triangleWidth / 2, triangleHeight);
        mPath.lineTo(trianglePosition, 0);
        mPath.lineTo(trianglePosition + triangleWidth /2, triangleHeight);
        mPath.lineTo(w - 20, triangleHeight);
        mPath.lineTo(w - 20, h - 20);
        mPath.lineTo(20, h - 20);
        mPath.close();

        mDestPath.reset();
        mDestPath.setFillType(EVEN_ODD);
        mDestPath.moveTo(0, triangleHeight);
        mDestPath.lineTo(trianglePosition - triangleWidth / 2, triangleHeight);
        mDestPath.lineTo(trianglePosition, 0);
        mDestPath.lineTo(trianglePosition + triangleWidth /2, triangleHeight);
        mDestPath.lineTo(w - 1, triangleHeight);
        mDestPath.lineTo(w - 1 , h - 1);
        mDestPath.lineTo(0, h - 1);
        mDestPath.close();
        invalidate();
    }
}
