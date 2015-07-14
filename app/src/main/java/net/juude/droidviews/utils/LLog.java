package net.juude.droidviews.utils;
import android.util.Log;
/**
 * Created by juude on 15-7-13.
 */
public class LLog {
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

}
