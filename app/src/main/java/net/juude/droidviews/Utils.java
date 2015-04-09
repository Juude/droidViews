
package net.juude.droidviews;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import java.lang.reflect.Method;

public class Utils {
    protected static final String TAG = "Utils";
    private static boolean DEBUG = true;

    public static int getDensity(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        return outMetrics.densityDpi;
    }

    public static ViewGroup.LayoutParams newLP(Context context, int width, int height) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                dp(context, width),
                dp(context, height)
                );
        return params;
    }

    public static int dp(Context context, int value) {
        if (value < 0)
        {
            return value;
        }
        return (context.getResources().getDisplayMetrics().densityDpi * value);
    }

    public static void logE(Object o, String msg, Throwable e)
    {
        if (DEBUG)
        {
            if (o instanceof Class)
            {
                Log.e(((Class<?>) o).getSimpleName(), msg, e);
            }
            else
            {
                Log.e(o.getClass().getSimpleName(), msg, e);
            }

        }
    }

    public static void logE(Object o, String msg)
    {
        logE(o, msg, null);
    }

    public static void addButton(final Fragment fragment,
            ViewGroup layout,
            final String title,
            final String onClick,
            final Class<?>[] parameters,
            final Object[] objects) {
        Button button = new Button(fragment.getActivity());
        LayoutParams params = new LayoutParams();
        params.width = 200 * getDensity(fragment.getActivity());
        params.height = 100;
        button.setLayoutParams(params);
        button.setText(title);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (parameters == null) {
                    invoke(fragment, onClick);
                }
                else
                {
                    invoke(fragment, onClick, parameters, objects);
                }
            }
        });
        layout.addView(button);
    }

    public static void addButton(final Fragment fragment,
            ViewGroup layout,
            final String title,
            final String onClick) {
        addButton(fragment, layout, title, onClick, null, null);
    }

    private static void invoke(
            final Fragment fragment,
            final String onClick,
            final Class<?>[] parameters,
            final Object[] objects) {
        try {
            Log.e(TAG, "class is " + fragment.getClass());
            Method method = fragment.getClass().getMethod(onClick, parameters);
            method.invoke(fragment, objects);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invoke(
            final Fragment fragment,
            final String onClick) {
        try {
            Log.e(TAG, "class is " + fragment.getClass());
            Method method = fragment.getClass().getMethod(onClick);
            method.invoke(fragment);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
