package com.juude.viewdemos;

import android.app.Application;

import com.juuda.droidmock.mock.Mocks;
import com.juude.viewdemos.debug.ViewDeugMocker;
import com.juude.viewdemos.dialog.DialogMocker;
import com.juude.viewdemos.mock.FragmentMocker;

public class DroidViewsApplication extends Application{

    @Override
    public void onCreate() {
        
        Mocks.sModuleMap.put("dialog", DialogMocker.class);
        Mocks.sModuleMap.put("viewdebug", ViewDeugMocker.class);
        Mocks.sModuleMap.put("fragment", FragmentMocker.class);
        
        super.onCreate();
    }
    
}
