
package net.juude.viewdemos.linkedbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class LinkedButton extends Button {

    public LinkedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LinkedButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkedButton(Context context) {
        this(context, null, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setTextColor(getResources().getColor(android.R.color.white));
                break;

            case MotionEvent.ACTION_UP:
                setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                break;
        }
        return super.onTouchEvent(event);
    }

}
