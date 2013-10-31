package com.lewa.viewtest.mock;

import android.content.Context;
import android.content.Intent;

import com.lewa.viewtest.CommonActivity;

public class MockUtils {
    public static void startFragment(Context context , String fragmentName) {
        Intent i = new Intent(context, CommonActivity.class);
        i.putExtra(CommonActivity.KEY_FRAGMENT, fragmentName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
