package net.juude.droidviews;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.juuda.droidmock.mock.Mocks;
import net.juude.droidviews.debug.ViewDeugMocker;
import net.juude.droidviews.mock.FragmentMocker;

public class DroidViewsApplication extends Application{

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
    }
    
}
