package net.juude.stethdemos;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.util.List;

/**
 * Created by juude on 16/6/8.
 */
public class ViewDumperPlugin implements DumperPlugin{

    @Override
    public String getName() {
        return "view";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        Activity topActivity = ActivityInfoProvider.getInstance().getTopActivityOrNull();
        if (topActivity == null) {
            return;
        }
        View topView = topActivity.getWindow().getDecorView();

    }

    private void getTopFragment(Activity topActivity) {
        Fragment topFragment = null;
        if (topActivity instanceof FragmentActivity) {
            List<Fragment> fragmentList = ((FragmentActivity) topActivity).getSupportFragmentManager().getFragments();
            if (fragmentList != null && fragmentList.size() > 0) {
                for (Fragment fragment : fragmentList) {
                    if (fragment.isResumed() && !fragment.isHidden()) {
                        topFragment = fragment;
                    }
                }
            }
        }
    }
}
