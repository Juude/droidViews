package net.juude.droidviews.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by juude on 15-8-21.
 */
public class CanvasSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private RenderThread mRenderThread;
    public static final String TAG = "CanvasSurfaceView";
    public CanvasSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        //setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRenderThread = new RenderThread(getHolder());
        mRenderThread.setEnabled(true);
        mRenderThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mRenderThread.setEnabled(false);
    }

    private static class RenderThread extends Thread {
        private  boolean mEnabled;
        private SurfaceHolder mSurfaceHolder;

        public RenderThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
        }

        public void setEnabled(boolean enabled) {
            mEnabled = enabled;
        }

        @Override
        public void run() {
            super.run();
            if(mEnabled) {
                Surface surface = mSurfaceHolder.getSurface();
                //Canvas canvas = mSurfaceHolder.lockCanvas();
                Canvas canvas = unlockHardwareCanvas(surface);
                canvas.drawColor(Color.RED);
                //mSurfaceHolder.unlockCanvasAndPost(canvas);
                surface.unlockCanvasAndPost(canvas);
                Log.d(TAG, "canvas : " + canvas.getClass().getSimpleName() + " is hardware accelerated " + canvas.isHardwareAccelerated());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public Canvas unlockHardwareCanvas(Surface surface) {
            try {
                Method method = Surface.class.getMethod("lockHardwareCanvas");
                try {
                    return (Canvas) method.invoke(surface);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
