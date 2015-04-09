
package net.juude.droidviews.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback2;
import android.view.SurfaceView;

public class StarFlagView extends SurfaceView implements Callback2, Runnable {

    private final static int STAR_COLOR = 0xffffff00;

    private final static int BACK_COLOR = 0xffff0000;

    private SurfaceHolder mHolder;

    private boolean isStop = false;

    private Thread mThread;

    private Path mBigStarPath;

    private Path[] mSmallStarsPath;

    private Paint mStarPaint;

    private int mWidth, mHeight;

    private Paint mBackPaint;

    public StarFlagView(Context context) {

        super(context);

        init(null, 0);

    }

    public StarFlagView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        init(attrs, defStyle);

    }

    public StarFlagView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(attrs, 0);

    }

    private void init(AttributeSet attrs, int defStyle) {

        mHolder = getHolder();

        mHolder.addCallback(this);

        setFocusable(true);

        setFocusableInTouchMode(true);

        setZOrderOnTop(true);

        mHolder.setFormat(PixelFormat.TRANSPARENT);// 设置背景透明

        mThread = new Thread(this);

    }

    public void run() {

        Canvas canvas = null;

        while (!isStop) {

            try {

                canvas = mHolder.lockCanvas();

                canvas.drawColor(Color.BLACK, Mode.CLEAR);// 清除屏幕

                canvas.drawRect(0, 0, mWidth, mHeight, mBackPaint);

                canvas.drawPath(mBigStarPath, mStarPaint);

                for (int i = 0; i < mSmallStarsPath.length; i++) {

                    canvas.drawPath(mSmallStarsPath[i], mStarPaint);

                }

            }
            catch (Exception e) {

                e.printStackTrace();

            }
            finally {

                if (canvas != null) {

                    mHolder.unlockCanvasAndPost(canvas);

                }

            }

            try {

                Thread.sleep(100);

            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,

            int height) {

    }

    public void surfaceCreated(SurfaceHolder holder) {

        // 大圆中心点

        int width = getWidth();

        int height = getHeight();

        if (width * 2 / 3 > height) {

            width = height / 2 * 3;

        }
        else {

            height = width / 3 * 2;

        }

        int cellWidth = width / 30;

        mWidth = width;

        mHeight = height;

        // 大五角星路径

        mBigStarPath = createStarPath(new PointF(width / 6, height / 4),

                width / 10, -90);

        // 小五角星路径

        mSmallStarsPath = new Path[4];

        mSmallStarsPath[0] = createStarPath(new PointF(cellWidth * 10,

                cellWidth * 2), cellWidth,

                (float) (Math.atan2(3, -5) / Math.PI * 180));

        mSmallStarsPath[1] = createStarPath(new PointF(cellWidth * 12,

                cellWidth * 4), cellWidth,

                (float) (Math.atan2(1, -7) / Math.PI * 180));

        mSmallStarsPath[2] = createStarPath(new PointF(cellWidth * 12,

                cellWidth * 7), cellWidth,

                (float) (Math.atan2(-2, 7) / Math.PI * 180));

        mSmallStarsPath[3] = createStarPath(new PointF(cellWidth * 10,

                cellWidth * 9), cellWidth,

                (float) (Math.atan2(-4, 5) / Math.PI * 180 - 90));

        mStarPaint = new Paint();

        mStarPaint.setColor(STAR_COLOR);

        mStarPaint.setStyle(Style.FILL);

        mStarPaint.setAntiAlias(true);// 抗锯齿

        mBackPaint = new Paint();

        mBackPaint.setColor(BACK_COLOR);

        mBackPaint.setStyle(Style.FILL);

        mThread.start();

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        isStop = true;

    }

    private Path createStarPath(PointF centerPointF, float radius, float rotate) {

        final double arc = Math.PI / 5;

        final double rad = Math.sin(Math.PI / 10) / Math.sin(3 * Math.PI / 10);

        Path path = new Path();

        path.moveTo(1, 0);

        for (int idx = 0; idx < 5; idx++) {

            path.lineTo((float) (rad * Math.cos((1 + 2 * idx) * arc)),

                    (float) (rad * Math.sin((1 + 2 * idx) * arc)));

            path.lineTo((float) (Math.cos(2 * (idx + 1) * arc)),

                    (float) (Math.sin(2 * (idx + 1) * arc)));

        }

        path.close();

        Matrix matrix = new Matrix();

        matrix.postRotate(rotate);

        matrix.postScale(radius, radius);

        matrix.postTranslate(centerPointF.x, centerPointF.y);

        path.transform(matrix);

        return path;

    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }
}
