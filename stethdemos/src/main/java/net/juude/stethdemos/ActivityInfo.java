package net.juude.stethdemos;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by sjd on 16/6/8.
 */
public class ActivityInfo implements Application.ActivityLifecycleCallbacks{
    private static ActivityInfo sInstance;
    private WeakReference<Activity> mTopActivity;

    public static ActivityInfo getInstance() {
        if (sInstance == null) {
            sInstance = new ActivityInfo();
        }
        return sInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mTopActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Nullable
    public Activity getTopActivityOrNull() {
        if (mTopActivity != null &&  mTopActivity.get() != null) {
            return mTopActivity.get();
        }
        return null;
    }
}
