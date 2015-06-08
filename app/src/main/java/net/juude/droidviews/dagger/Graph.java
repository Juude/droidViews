package net.juude.droidviews.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by juude on 15-6-3.
 */
@Singleton
@Component(modules = {DaggerModule.class})
public interface Graph {

    public void inject(SomeThing someThing);

    public final static class Initializer {
        public static Graph init() {
            //return builder()
            //        .build();
            return null;
        }
    }
}
