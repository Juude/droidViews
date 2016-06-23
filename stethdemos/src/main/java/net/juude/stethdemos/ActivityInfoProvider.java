package net.juude.stethdemos;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Stack;

/**
 * Created by sjd on 16/6/8.
 */
public class ActivityInfoProvider implements Application.ActivityLifecycleCallbacks{
    private static ActivityInfoProvider sInstance;
    private Activity mTopActivity;
    private Stack<Activity> mActivityStack = new Stack<>();
    public static ActivityInfoProvider getInstance() {
        if (sInstance == null) {
            sInstance = new ActivityInfoProvider();
        }
        return sInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActivityStack.push(activity);
        mTopActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActivityStack.pop();
        mTopActivity = null;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Nullable
    public Activity getTopActivityOrNull() {
        return mTopActivity;
    }

    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }
}
