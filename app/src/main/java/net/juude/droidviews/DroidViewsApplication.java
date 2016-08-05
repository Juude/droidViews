package net.juude.droidviews;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

public class DroidViewsApplication extends Application{
    private static final String TAG = "DroidViewsApplication";
    private static DroidViewsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sInstance = this;
    }

    public static DroidViewsApplication getInstance() {
        return sInstance;
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
