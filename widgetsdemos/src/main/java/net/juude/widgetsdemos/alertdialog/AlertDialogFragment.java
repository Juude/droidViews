package net.juude.widgetsdemos.alertdialog;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import net.juude.widgetsdemos.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by juude on 16/7/23.
 */
@RuntimePermissions
public class AlertDialogFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_alert_dialog, null);
        v.findViewById(R.id.showalertdialog).setOnClickListener(this);
        v.findViewById(R.id.showdialog).setOnClickListener(this);
        v.findViewById(R.id.show_dialog_activity).setOnClickListener(this);
        return v;
    }


    private void createAndShowAlertDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_alert_dialog, null);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void startDialogActivity() {
        Intent intent = new Intent(getActivity(), DialogActivity.class);
        startActivity(intent);
    }


    @NeedsPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
    public void createAndShowDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_alert_dialog, null);
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(v);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showalertdialog:
                createAndShowAlertDialog();
                break;
            case R.id.showdialog:
                if (Build.VERSION.SDK_INT >= 23) {
                    checkAndShowDialog();
                }
                break;
            case R.id.show_dialog_activity:
                startDialogActivity();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndShowDialog() {
        if (!Settings.canDrawOverlays(getContext())) {
            PermissionRequester.getInstance().checkSettingAlertPermission(this, 333);
        } else {
            createAndShowDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            createAndShowDialog();
        }
    }
}
