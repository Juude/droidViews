package net.juude.droidviews.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by juude on 15-5-28.
 */
public class CircleLayout extends FrameLayout{

    private int mCornerRadius = 50;

    public CircleLayout(Context context) {
        this(context, null, 0);
    }

    public CircleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    private Path mPath = new Path();
    private RectF mRectF = new RectF();

    @Override
    public void draw(Canvas canvas) {
        int count = canvas.save();
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.restoreToCount(count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mRectF.set(0, 0, w, h);
        mPath.addOval(mRectF, Path.Direction.CW);
        mPath.close();
    }
}
