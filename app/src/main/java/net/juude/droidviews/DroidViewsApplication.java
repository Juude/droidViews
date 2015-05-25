package net.juude.droidviews;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;

import net.juude.droidviews.fresco.SimpleDraweeView;


public class DroidViewsApplication extends Application{

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        initializeDrawee(this);
        super.onCreate();
    }

    private static void initializeDrawee(Context context) {
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(context));
    }
}
