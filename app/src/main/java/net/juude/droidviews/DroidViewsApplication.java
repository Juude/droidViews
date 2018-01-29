package net.juude.droidviews;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder;

import java.util.Random;

public class DroidViewsApplication extends Application{
    private static final String TAG = "DroidViewsApplication";
    private static DroidViewsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Stetho.initialize(Stetho.newInitializerBuilder(DroidViewsApplication.this)
                .enableWebKitInspector(new InspectorModulesProvider() {
                    @Override
                    public Iterable<ChromeDevtoolsDomain> get() {
                        return new Stetho.DefaultInspectorModulesBuilder(DroidViewsApplication.this).runtimeRepl(
                                new JsRuntimeReplFactoryBuilder(DroidViewsApplication.this)
                                        // Pass to JavaScript: var foo = "bar";
                                        .addVariable("foo", "bar")
                                        .build()
                        ).finish();
                    }
                })
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return null;
                    }
                })
                .build());
        //createSyncAccount(this);

    }

    private void createSyncAccount(Context context) {
        Account newAccount = new Account(Consts.ACCOUNT_NAME, Consts.ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        try {
            if (accountManager.addAccountExplicitly(newAccount, null, null)) {
                /*
                 * If you don't set android:syncable="true" in
                 * in your  element in the manifest,
                 * then call context.setIsSyncable(account, AUTHORITY, 1)
                 * here.
                 */
                ContentResolver.setIsSyncable(newAccount, Consts.AUTHORITY, 1);
                ContentResolver.setSyncAutomatically(newAccount, Consts.AUTHORITY, true);
                Random random = new Random();
                int interval = 1800 + random.nextInt(600);
                ContentResolver.addPeriodicSync(newAccount, Consts.AUTHORITY, Bundle.EMPTY, interval);
            } else {
                /*
                 * The account exists or some other error occurred. Log this, report it,
                 * or handle it internally.
                 */
            }
        } catch (Exception e) {
            Log.w("SplashActivity", "createSyncAccount err", e);
        }
    }

    public static DroidViewsApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    public static class Consts {

        public static final String AUTHORITY = "lstretailer_AUTHORITY";
        public static String ACCOUNT_NAME = "lstretailer_ACCOUNT_NAME";
        public static String ACCOUNT_TYPE = "lstretailer_ACCOUNT_TYPE";

    }
}
