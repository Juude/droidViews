package net.juude.testdemos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int _REQUEST_IMAGE_CAPTURE = 2;
    private static final String TAG = "MainActivity";
    private File mCurrentPhotoFile;
    private static final int PHOTO_WIDTH = 720; //原图片压缩宽
    private static final int PHOTO_HEIGHT = 1280;//原图片压缩高
    private ImageView mImageView;
    public static MyHandler sMyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(android.R.id.content);
        setContentView(frameLayout);
        //setContentView(R.layout.activity_main);
//        final Button buttonStartCamera = (Button) findViewById(R.id.startCamera);
//        mImageView = (ImageView) findViewById(R.id.image);
//        assert buttonStartCamera != null;
//        sMyHandler = new MyHandler(this);
//        buttonStartCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // Ensure that there's a camera activity to handle the intent
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    // Create the File where the photo should go
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                        ex.printStackTrace();
//                    }
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
//                        startActivityForResult(takePictureIntent, _REQUEST_IMAGE_CAPTURE);
//                    }
//                }
//            }
//        });
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                Fragment.instantiate(this, AnimationDemoFragment.class.getName()))
                .commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == _REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<String> list = new ArrayList<String>();
                try {
                    PhotoConverter.getInstance().convertImage(mCurrentPhotoFile.getAbsolutePath(),
                            mCurrentPhotoFile.getAbsolutePath());
                    onFileGet();
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoFile = image;
        return image;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sMyHandler = null;
    }

    private static class MyHandler extends Handler {
        private final MainActivity mMainActivity;

        public MyHandler(MainActivity mainActivity) {
            mMainActivity = mainActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mMainActivity.onFileGet();
        }
    }

    private void onFileGet() {
        mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoFile.getAbsolutePath()));
    }
}
