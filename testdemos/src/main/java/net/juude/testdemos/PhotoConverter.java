package net.juude.testdemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by juude on 16/5/17.
 * 图片旋转工具,主要是因为某些机型上竖屏问题
 * 主要代码复制自:https://github.com/mylhyl/AndroidTakePhoto/blob/afcd1c5c0bacc519462faab9e7ac3a4c362a9f6a/takephoto%2Fsrc%2Fmain%2Fjava%2Fcom%2Fmylhyl%2Ftakephoto%2FTakePhotoOptions.java
 */
public class PhotoConverter {
    private static final int PHOTO_WIDTH = 720; //原图片压缩宽
    private static final int PHOTO_HEIGHT = 1280;//原图片压缩高
    private static final String TAG = "PhotoConverter";
    private static PhotoConverter sInstance;
    public static PhotoConverter getInstance() {
        if (sInstance == null) {
            sInstance = new PhotoConverter();
        }
        return sInstance;
    }

    public boolean convertImage(String originalPath, String compressedPath) throws Exception {
        if (readPictureDegree(originalPath) == 0) {
            return true;
        }
        Bitmap bitmapFromFile = getBitmapFromFile(originalPath, PHOTO_WIDTH, PHOTO_HEIGHT);
        return saveBitmapToLocal(bitmapFromFile, compressedPath);
    }

    private int readPictureDegree(String path) throws Exception {
        int degree = 0;
        ExifInterface exifInterface = new ExifInterface(path);
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
        }
        return degree;
    }

    private Bitmap getBitmapFromFile(String path, int width, int height) throws Exception {
        BitmapFactory.Options opts = null;
        if (path != null) {
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                ////设置为true, 加载器不会返回图片, 而是设置Options对象中以out开头的字段.即仅仅解码边缘区域
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, opts);
                final int minSideLength = Math.min(width, height);
                //设置位图缩放比例
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                // 指定加载可以加载出图片.
                opts.inJustDecodeBounds = false;
                //为位图设置100K的缓存
                opts.inTempStorage = new byte[100 * 1024];
                //设置位图颜色显示优化方式
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                //设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
                opts.inPurgeable = true;
                //设置解码位图的尺寸信息
                opts.inInputShareable = true;
            }
            //解码位图
            Bitmap thbBitmap = BitmapFactory.decodeFile(path, opts);
            //获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
            int degree = readPictureDegree(path);
            Bitmap rotateBitmap = rotateImageView(degree, thbBitmap);
            return rotateBitmap;
        } else return null;
    }

    private int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private Bitmap rotateImageView(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 根据bitmap保存图片到本地
     *
     * @param bitmap
     * @return
     */
    private  boolean saveBitmapToLocal(Bitmap bitmap, String filePath) {
        if (null == bitmap) {
            return false;
        }
        FileOutputStream fileOutput = null;
        try {
            File fileDir = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File imgFile = new File(filePath);
            if (!imgFile.exists())
                imgFile.createNewFile();
            fileOutput = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutput);
            fileOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != fileOutput) {
                try {
                    fileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "result path :" + filePath);
        return true;
    }
}
