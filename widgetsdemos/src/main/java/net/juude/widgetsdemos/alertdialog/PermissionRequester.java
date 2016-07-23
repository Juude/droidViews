package net.juude.widgetsdemos.alertdialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import net.juude.widgetsdemos.PermissionShadowActivity;

import java.util.Random;

/**
 * Created by juude on 16/7/23.
 */
public class PermissionRequester {
    private static final String TAG = "PermissionRequester";
    private SparseArray<Runnable> mRunnableMap = new SparseArray<>();

    public void doWithPermission(Activity context, String permission, final Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 23) {
            switch (context.checkSelfPermission(permission)) {
                case PackageManager.PERMISSION_GRANTED:
                    runnable.run();
                    break;
                case PackageManager.PERMISSION_DENIED:
                    if (!context.shouldShowRequestPermissionRationale(permission)) {
                        requestForPermission(context, permission, runnable);
                    } else {
                        Toast.makeText(context, "denied", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        } else {
            runnable.run();
        }
    }

    private void requestForPermission(Activity context, String permission, Runnable runnable) {
        Intent intent = new Intent(context, PermissionShadowActivity.class);
        int runnableId = new Random().nextInt(10000);
        intent.putExtra("permission", permission);
        intent.putExtra("id", runnableId);
        mRunnableMap.put(runnableId, runnable);
        context.startActivity(intent);
    }

    public void onPermissionResult(String permission, int grantResult, int requestCode) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            Runnable runnable = mRunnableMap.get(requestCode);
            runnable.run();
        }
        mRunnableMap.remove(requestCode);
    }

    private PermissionRequester() {
    }

    private static PermissionRequester sInstance;
    public static PermissionRequester getInstance() {
        if (sInstance == null) {
            sInstance = new PermissionRequester();
        }
        return sInstance;
    }

    @TargetApi(23)
    public boolean checkSettingAlertPermission(Object cxt, int req) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            if (!Settings.canDrawOverlays(activity.getBaseContext())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, req);
                return false;
            }
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            if (!Settings.canDrawOverlays(fragment.getActivity())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + fragment.getActivity().getPackageName()));
                fragment.startActivityForResult(intent, req);
                return false;
            }
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }

        return true;
    }
}
