package top.perf.utils;

import android.content.Context;
import android.view.View;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by juude on 2016/12/2.
 */

public class UIUtils {

    public static int[] getLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    public static int bottomInWindow(View view) {
        return topInWindow(view) + view.getHeight();
    }

    public static int topInWindow(View view) {
        return getLocationInWindow(view)[1];
    }

    public static int convertDpToPx(Context context, float dp) {
        return (int) applyDimension(COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
