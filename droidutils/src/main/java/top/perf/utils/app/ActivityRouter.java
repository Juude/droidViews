package top.perf.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import top.perf.utils.collect.Preconditions;

/**
 * Created by juude on 16/8/8.
 */
public class ActivityRouter {
    private static ActivityRouter sInstance;
    private Context context;
    private Class<? extends Activity> activity;
    private Bundle bundles;

    public static ActivityRouter get() {
        if (sInstance == null) {
            sInstance = new ActivityRouter();
        }
        return sInstance;
    }

    public void go() {
        Intent intent = new Intent(context, activity);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (bundles != null) {
            intent.replaceExtras(bundles);
        }
        context.startActivity(intent);
    }

    public static Builder router() {
        return new Builder();
    }

    public static Builder router(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context context;
        private Class<? extends Activity> activity;
        private Bundle bundles;

        public Builder() {

        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder context(Context context) {
            Preconditions.checkNotNull(context);
            this.context = context;
            return this;
        }

        public Builder to(Class<? extends Activity> activity) {
            Preconditions.checkNotNull(context);
            this.activity = activity;
            return this;
        }

        public ActivityRouter build() {
            ActivityRouter router = new ActivityRouter();
            router.context = context;
            router.activity = activity;
            router.bundles = bundles;
            return router;
        }

        public Builder extra(String key, long i) {
            getBundle().putLong(key, i);
            return this;
        }

        private Bundle getBundle() {
            if (bundles == null) {
                bundles = new Bundle();
            }
            return bundles;
        }

        public void go() {
            build().go();
        }
    }

}
