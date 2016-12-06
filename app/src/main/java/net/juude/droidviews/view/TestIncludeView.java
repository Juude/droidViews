package net.juude.droidviews.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import net.juude.droidviews.R;
/**
 *
 * this project is to show whether id can override the old one
 * */

public class TestIncludeView extends RelativeLayout{

    public TestIncludeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        findViewById(R.id.included).setBackgroundColor(Color.GREEN);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.override_id).setBackgroundColor(Color.RED);
            }
        }, 1000);
        super.onFinishInflate();
    }
}
