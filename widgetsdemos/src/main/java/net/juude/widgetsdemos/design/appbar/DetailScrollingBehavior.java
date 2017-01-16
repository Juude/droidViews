package net.juude.widgetsdemos.design.appbar;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import top.perf.utils.UIUtils;

/**
 * Created by juude on 2016/12/2.
 */

public class DetailScrollingBehavior extends AppBarLayout.ScrollingViewBehavior {

    private int mToolBarHeight = -1;

    public DetailScrollingBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        if (mToolBarHeight == -1) {
            mToolBarHeight = UIUtils.convertDpToPx(parent.getContext(), 50);
        }
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed + mToolBarHeight);
    }

}
