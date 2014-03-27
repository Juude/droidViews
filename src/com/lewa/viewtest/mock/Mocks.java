package com.lewa.viewtest.mock;

import android.content.Context;
import android.os.Bundle;

import com.lewa.viewtest.mock.MockUtils.Mock;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Mocks {
    private static Mocks sInstance = null;
   
    public static Mocks getInstance() {
        synchronized(Mocks.class) {
            if(sInstance == null) {
                sInstance = new Mocks();
            }
            return sInstance;
        }
    }
    
    private Mocks() {
      
    }
    
    public static HashMap<String, Class<? extends Mocker>> sModuleMap = new HashMap<String, Class<? extends Mocker>>();
    
    static {
        //sModuleMap.put("alarmManagerTest", AlarmManagerTest.class);
    }
    
    
    @Mock
    public void dump(Context context, Bundle bundle) {
        final String module = MockUtils.getString(bundle, "notification", "notification");
        Class<? extends Mocker> clazz = sModuleMap.get(module);    
        try {
            Constructor<? extends Mocker> mockerConstructor = clazz.getConstructor(Context.class, Bundle.class);
            Mocker mocker = (Mocker) mockerConstructor.newInstance(context, bundle);
            mocker.dump();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
