package net.juude.widgetsdemos.popup;

import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import net.juude.widgetsdemos.R;

/**
 * Created by juude on 2016/12/6.
 * create a popup window from View.
 */

public class PopupWindowCreator {
    public static PopupWindow create(View view) {
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        popupWindow.setOutsideTouchable(false);
        return popupWindow;
    }
}
