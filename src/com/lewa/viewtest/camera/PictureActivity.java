
package com.lewa.viewtest.camera;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.lewa.viewtest.R;

public class PictureActivity extends Activity {
    private SurfaceHolder mSurfaceHolder = null;
    private PictureUtils mPictureUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SurfaceView mSurfaceView = new SurfaceView(this);
        setContentView(R.layout.picture_activity);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mPictureUtils = new PictureUtils(mSurfaceHolder);
        super.onCreate(savedInstanceState);
    }

    public void takePhoto(View v)
    {
        mPictureUtils.takePicture();
    }

    @Override
    public void onBackPressed() {
        mPictureUtils.takePicture();
        super.onBackPressed();
    }

}
