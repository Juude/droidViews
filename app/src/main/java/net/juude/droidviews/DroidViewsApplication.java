package net.juude.droidviews;

import android.app.Application;

import com.juuda.droidmock.mock.Mocks;
import net.juude.droidviews.debug.ViewDeugMocker;
import net.juude.droidviews.mock.FragmentMocker;

public class DroidViewsApplication extends Application{

    @Override
    public void onCreate() {
        
        Mocks.sModuleMap.put("viewdebug", ViewDeugMocker.class);
        Mocks.sModuleMap.put("fragment", FragmentMocker.class);
        
        super.onCreate();
    }
    
}
