
package com.lewa.viewtest.camera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.graphics.ImageFormat;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureUtils {
    private static final String TAG = "PictureUtils";
    private Camera mCamera;
    private String mTempPath = Environment.getExternalStorageDirectory()
            .getPath() + File.separator + "0000.jpg";;
    private SurfaceHolder mSurfaceHolder;
    private final int ROTATION = 270;

    public PictureUtils(SurfaceHolder holder)
    {
        mSurfaceHolder = holder;
    }

    public void takePicture()
    {
        try
        {
            mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            Camera.Parameters params = mCamera.getParameters();

            params.setPictureFormat(ImageFormat.JPEG);
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            params.setRotation(ROTATION);
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
            mCamera.setParameters(params);

            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            Log.e(TAG, "before");
            mCamera.takePicture(null, null, mPictureTakenCallback);
            Log.e(TAG, "taking...");

        }
        catch (Exception e)
        {
            Log.e(TAG, Log.getStackTraceString(e));
            closeCamera();
        }

    }

    private void closeCamera()
    {
        if (mCamera != null)
        {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    private Camera.PictureCallback mPictureTakenCallback = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e(TAG, "data.." + data + "to" + mTempPath);
            closeCamera();
            File dest = new File(mTempPath);
            if (dest.exists())
            {
                dest.delete();
            }
            try {
                FileOutputStream fop = new FileOutputStream(dest);
                fop.write(data);
                fop.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        }
    };
}
