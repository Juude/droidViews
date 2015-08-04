package net.juude.droidviews;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;

import net.juude.droidviews.dagger.Graph;
import net.juude.droidviews.fresco.SimpleDraweeView;

public class DroidViewsApplication extends Application{
    private static final String TAG = "DroidViewsApplication";
    private static DroidViewsApplication sInstance;
    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initializeDrawee(this);
        mGraph = Graph.Initializer.init();
        sInstance = this;
    }

    public static DroidViewsApplication getInstance() {
        return sInstance;
    }

    private static void initializeDrawee(Context context) {
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(context));
    }

    public Graph getGraph() {
        return mGraph;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }
}
