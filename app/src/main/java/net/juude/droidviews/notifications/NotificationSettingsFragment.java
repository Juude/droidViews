package net.juude.droidviews.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import net.juude.droidviews.R;

/**
 * Created by Juude 2017/12/15.
 */

public class NotificationSettingsFragment extends Fragment {

    private boolean mNotificationsEnabled = false;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mButton = new Button(getActivity());
        mButton.setText("checkNotification");
        mButton.setOnClickListener(view -> playNotification());
        return mButton;
    }

    public void checkNotification() {
        mNotificationsEnabled =  NotificationManagerCompat.from(getActivity()).areNotificationsEnabled();
        String info = "notification enabled: " + mNotificationsEnabled  + "eeee";
        Log.e("Notification", info);
        Toast.makeText(getActivity(),
                info,
                Toast.LENGTH_LONG
                ).show();
        mButton.postDelayed(() -> goToNotificationSettings(getActivity()), 1000);
    }

    private void playNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                    .setSmallIcon(R.drawable.button_background)
                    .setContentTitle("Ping Notification")
                    .setContentText("Tomorrow will be your birthday.")
                    .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                    .setPriority(NotificationCompat.PRIORITY_HIGH); //must give priority to High, Max which will considered as heads-up notification
;

////set intents and pending intents to call service on click of "dismiss" action button of notification
//        Intent dismissIntent = new Intent(this, MyService.class);
//        dismissIntent.setAction(ACTION_DISMISS);
//        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);
//
////set intents and pending intents to call service on click of "snooze" action button of notification
//        Intent snoozeIntent = new Intent(this, MyService.class);
//        snoozeIntent.setAction(ACTION_SNOOZE);
//        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);

// Gets an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//to post your notification to the notification bar with a id. If a notification with same id already exists, it will get replaced with updated information.
        notificationManager.notify(1000, builder.build());
    }

    public void goToNotificationSettings(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        context.startActivity(intent);
    }
}
