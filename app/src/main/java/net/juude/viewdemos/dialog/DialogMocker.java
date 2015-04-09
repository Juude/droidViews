package net.juude.viewdemos.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.Mocker;
/**
 * This displays how dialogs works
 * for more information, please refer to http://developer.android.com/reference/android/app/AlertDialog.html
 * 1. AlertDialog cannot show in context receiver  : http://stackoverflow.com/questions/8766739/show-an-alert-dialog-in-broadcast-receiver-after-a-system-reboot
 * 2. 
 */
public class DialogMocker extends Mocker{

    private static final String TAG = "DialogMocker";

    public DialogMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        Log.d(TAG, "showing alert dialg");
        new AlertDialog.Builder(mContext.getApplicationContext()).
            setTitle("dialog title").
            setMessage("hello I am a dialog").create().show();
    }

}
