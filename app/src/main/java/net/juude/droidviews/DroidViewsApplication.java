package net.juude.droidviews;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;

import net.juude.droidviews.dagger.Graph;
import net.juude.droidviews.fresco.SimpleDraweeView;


public class DroidViewsApplication extends Application{
    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initializeDrawee(this);
        mGraph = Graph.Initializer.init();
    }

    private static void initializeDrawee(Context context) {
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(context));
    }

    public Graph getGraph() {
        return mGraph;
    }

}
