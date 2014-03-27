package com.lewa.viewtest.mock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.lewa.viewtest.CommonActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MockUtils {
    @Retention(RetentionPolicy.RUNTIME)
    static public @interface Mock {
    }
    
    public static String getString(Bundle bundle, String key, String def) {
        String result = bundle.getString(key);
        result = result == null ? def : result;
        return result;
    }
    
    public static int getInt(Bundle bundle, String key, int def) {
        String str = bundle.getString(key);
        int result = def;
        try {
            result = Integer.parseInt(str);
        }
        catch(Exception e) {
        }
        return result; 
    }
    
    public static void startFragment(Context context , String fragmentName) {
        Intent i = new Intent(context, CommonActivity.class);
        i.putExtra(CommonActivity.KEY_FRAGMENT, fragmentName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
