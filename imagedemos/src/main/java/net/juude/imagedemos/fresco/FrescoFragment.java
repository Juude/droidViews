package net.juude.imagedemos.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.juude.imagedemos.R;


/**
 * Created by juude on 15/5/24.
 */
public class FrescoFragment extends Fragment {
    private SimpleDraweeView bigImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fresco, null);
//
//        SimpleDraweeView draweeMore = (SimpleDraweeView) v.findViewById(R.id.drawee_more);
//        draweeMore.setImageURI(Uri.parse("http://www.artisan.com.tw/images/blogs/DSC02163.JPG"));
//
//        SimpleDraweeView circle_drawee = (SimpleDraweeView) v.findViewById(R.id.circle_drawee);
//        circle_drawee.setImageURI(Uri.parse("https://41.media.tumblr.com/e5f73c5a527b5fe418f32273c548aa46/tumblr_noj02v533u1qgn23wo1_1280.jpg"));
//
//        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(R.id.default_loading);
//        draweeView.setImageURI(Uri.parse("https://40.media.tumblr.com/7589f12f781f7dc9013ed7506e1334e4/tumblr_notbiy66no1urqmrvo1_500.jpg"));
//        draweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
//
//        SimpleDraweeView scale_center = (SimpleDraweeView) v.findViewById(R.id.scale_center);
//        scale_center.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
//
//        SimpleDraweeView scale_center_inside = (SimpleDraweeView) v.findViewById(R.id.scale_center_inside);
//        scale_center_inside.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
//
//        SimpleDraweeView scale_center_crop = (SimpleDraweeView) v.findViewById(R.id.scale_center_crop);
//        scale_center_crop.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
//        scale_center_crop.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
//
//        SimpleDraweeView scale_fit_center = (SimpleDraweeView) v.findViewById(R.id.scale_fit_center);
//        scale_fit_center.setImageBitmap(null);
//        scale_fit_center.setImageURI(Uri.parse("http://www.iapps.im/public/uploadfiles/icons/276ed0e2790fe3d1faf70b5e51e0c61e.jpg"));
         bigImage = (SimpleDraweeView)v.findViewById(R.id.strech_image);
        //bigImage.setImageURI(Uri.parse("http://www.imgbase.info/images/safe-wallpapers/photography/water/20814_water_wave_big_wave.jpg"));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("sss");
        Log.d("eee", "dds");
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                .build();
        //String url = "http://www.imgbase.info/images/safe-wallpapers/photography/water/20814_water_wave_big_wave.jpg";
        //String url = "http://cimg.taohuaan.net/upload/201211/09/161159Wq7P8.jpg";
        String url = "https://img.alicdn.com/tfs/TB1RjL3oMoQMeJjy0FoXXcShVXa-750-320.png";
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setImageDecodeOptions(decodeOptions)
                .setAutoRotateEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .build();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                Log.d("tag",
                        String.format("Final image received! " + "Size %s x %s" +
                        "Quality level %s, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality())
                        );
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                Log.d("","Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Log.e(getClass().getCanonicalName(), "Error loading %s", throwable);
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setControllerListener(controllerListener)
                .setImageRequest(request)
                .setUri(Uri.parse(url))
                .build();
        bigImage.setController(controller);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
