
package net.juude.droidviews.widget.textview.linkedbutton;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkedText extends TextView {

    private SpannableStringBuilder mBackgrounBuilder = null;
    private SpannableStringBuilder mNormalBuilder = null;
    private static final int BACKGROUND_BUILDER = 0;
    private static final int NORMAL_BUILDER = 1;

    public LinkedText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LinkedText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkedText(Context context) {
        this(context, null, 0);
    }

    private SpannableStringBuilder getStringBuilder(int type)
    {
        CharSequence text = getText();
        System.out.println("text = " + text);
        if (type == BACKGROUND_BUILDER) {
            if (mBackgrounBuilder == null) {
                mBackgrounBuilder = new SpannableStringBuilder(text);
                mBackgrounBuilder.setSpan(
                        new BackgroundColorSpan(getResources().getColor(
                                android.R.color.holo_blue_light)),
                        0,
                        text.length(),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            return mBackgrounBuilder;
        }
        else {
            if (mNormalBuilder == null) {
                mNormalBuilder = new SpannableStringBuilder(text);
                mNormalBuilder.setSpan(
                        new BackgroundColorSpan(getResources()
                                .getColor(android.R.color.transparent)),
                        0,
                        text.length(),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            return mNormalBuilder;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("action down....");
                setText(getStringBuilder(BACKGROUND_BUILDER));
                setTextColor(getResources().getColor(android.R.color.white));
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("action up....");
                setText(getStringBuilder(NORMAL_BUILDER));
                setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                break;
        }
        return true;
    }

}
