package net.juude.stethdemos;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;


/**
 * Created by juude on 16/5/19.
 */
public class StethoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(StethoApplication.this)
                .enableWebKitInspector(new InspectorModulesProvider() {
                    @Override
                    public Iterable<ChromeDevtoolsDomain> get() {
                        return new Stetho.DefaultInspectorModulesBuilder(StethoApplication.this).runtimeRepl(
                                new JsRuntimeReplFactoryBuilder(StethoApplication.this)
                                        // Pass to JavaScript: var foo = "bar";
                                        .addVariable("foo", "bar")
                                        .build()
                        ).finish();
                    }
                })
                .build());
        registerActivityLifecycleCallbacks(ActivityInfo.getInstance());
    }

}
