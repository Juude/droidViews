package net.juude.droidviews;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;

public class DroidViewsApplication extends Application{
    private static final String TAG = "DroidViewsApplication";
    private static DroidViewsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Stetho.initialize(Stetho.newInitializerBuilder(DroidViewsApplication.this)
                .enableWebKitInspector(new InspectorModulesProvider() {
                    @Override
                    public Iterable<ChromeDevtoolsDomain> get() {
                        return new Stetho.DefaultInspectorModulesBuilder(DroidViewsApplication.this).runtimeRepl(
                                new JsRuntimeReplFactoryBuilder(DroidViewsApplication.this)
                                        // Pass to JavaScript: var foo = "bar";
                                        .addVariable("foo", "bar")
                                        .build()
                        ).finish();
                    }
                })
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return null;
                    }
                })
                .build());
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
