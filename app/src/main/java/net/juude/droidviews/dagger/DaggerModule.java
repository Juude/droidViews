package net.juude.droidviews.dagger;

import dagger.Provides;

/**
 * Created by juude on 15-6-5.
 */
@dagger.Module
public class DaggerModule {

    @Provides
    public String providesName() {
        return "Hey Jude";
    }
}
