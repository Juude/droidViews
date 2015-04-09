package net.juude.droidviews.mock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;
import net.juude.droidviews.CommonActivity;

public class FragmentMocker extends Mocker {

    public FragmentMocker(Context context, Bundle extras) {
        super(context, extras);
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

    @Override
    public void dump() {
        startFragment(mContext, MockUtils.getString(mExtras, "fragment", "net.juude.droidviews.CommonFragment"));
    }
}