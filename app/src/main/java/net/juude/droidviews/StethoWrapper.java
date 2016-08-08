package net.juude.droidviews;

import android.app.Application;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juude on 16/8/5.
 */
public class StethoWrapper {

    private static List<DumperPlugin> sDumperList = new ArrayList<>();
    static {
       // sDumperList.add(new AlarmPlugin());
    }

    public void onCreate(final Application application) {
        Stetho.initialize(Stetho.newInitializerBuilder(application)
                .enableWebKitInspector(new InspectorModulesProvider() {
                    @Override
                    public Iterable<ChromeDevtoolsDomain> get() {
                        return new Stetho.DefaultInspectorModulesBuilder(application).runtimeRepl(
                                new JsRuntimeReplFactoryBuilder(application)
                                        // Pass to JavaScript: var foo = "bar";
                                        .addVariable("foo", "bar")
                                        .build()
                        ).finish();
                    }
                })
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return sDumperList;
                    }
                })
                .build());
    }
}
