package net.juude.widgetsdemos.design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import net.juude.widgetsdemos.R;

/**
 * Created by juude on 2016/11/16.
 */

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency.getId() == R.id.header;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        child.setTranslationY(dependency.getTranslationY() + dependency.getHeight());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        View headerView = coordinatorLayout.getDependencies(child).get(0);
        float translationY = headerView.getTranslationY();
        float translated = translationY + (-dy);
        if (translated > 0) {
            translated = 0;
            consumed[1] = (int) translationY;
        } else if (translated < -headerView.getHeight()) {
            translated = -headerView.getHeight();
            consumed[1] = (int) (headerView.getHeight() + translationY);
        } else {
            consumed[1] = dy;
        }
        headerView.setTranslationY(translated);
        consumed[0] = 0;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        coordinatorLayout.offsetTopAndBottom(-dyUnconsumed);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }
}
