package net.juude.widgetsdemos.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;

import net.juude.widgetsdemos.R;

import top.perf.utils.UIUtils;

/**
 * Created by juude on 2016/12/2.
 */

public class SwitchAppBarBehavior extends AppBarLayout.Behavior {

    private NestedScrollView mMainScroll;
    private View mDividerSection;
    private View mBottomPanel;
    private View mProductDetailsMore;
    private View mToolbar;
    private int mOffsetToLoadMore;
    private int OFFSET_TO_LOAD_MORE = 100;
    private boolean mIsLoadingMore = false;
    private static final int PAGE_ANIMATION_DURATION = 500;

    private boolean mDetailsVisible = false;
    public final static String TAG = "DetailAppBarBehavior";

    private NestedScrollView mSubScroll;

    public SwitchAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOffsetToLoadMore =  UIUtils.convertDpToPx(context, OFFSET_TO_LOAD_MORE);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        if (!mIsLoadingMore) {
            return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
        } else {
            return true;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (!mIsLoadingMore) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
        if (dyUnconsumed == 0) {
            return;
        }
        NestedScrollView scrollView = mIsLoadingMore ? subScroll(coordinatorLayout) : mainScroll(coordinatorLayout);
        int translationY = (int) (scrollView.getTranslationY() - dyUnconsumed);
        if (mIsLoadingMore && translationY < 0 || !mIsLoadingMore && translationY > 0) {
            translationY = 0;
        }
        scrollView.setTranslationY(translationY);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (mIsLoadingMore) {
            return false;
        } else {
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        if (mIsLoadingMore) {
            return false;
        } else {
            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }
    }

    private View toolbar(CoordinatorLayout parent) {
        if (mToolbar == null) {
            mToolbar = parent.findViewById(R.id.toolbar);
        }
        return mToolbar;
    }

    private View bootomPanel(CoordinatorLayout parent) {
        if (mBottomPanel == null) {
            mBottomPanel = parent.findViewById(R.id.bottom_panel);
        }
        return mBottomPanel;
    }

    private NestedScrollView mainScroll(CoordinatorLayout parent) {
        if (mMainScroll == null) {
            mMainScroll = (NestedScrollView) parent.findViewById(R.id.main_scroll);
        }
        return mMainScroll;
    }

    private NestedScrollView subScroll(CoordinatorLayout parent) {
        if (mSubScroll == null) {
            mSubScroll = (NestedScrollView) parent.findViewById(R.id.sub_scroll);
        }
        return mSubScroll;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
        super.onStopNestedScroll(coordinatorLayout, abl, target);
        mMainScroll = mainScroll(coordinatorLayout);
        mSubScroll = subScroll(coordinatorLayout);
        if (mIsLoadingMore) {
            if (mSubScroll.getTranslationY() > UIUtils.convertDpToPx(coordinatorLayout.getContext(), 100)) {
                Log.d(TAG, "jump");
                animateToMain();
            } else {
                mSubScroll.animate().translationY(0).setDuration(300).start();
            }
        } else {
            if (mMainScroll.getTranslationY() < -UIUtils.convertDpToPx(coordinatorLayout.getContext(), 100)) {
                Log.d(TAG, "jump");
                animateToSub();
            } else {
                mMainScroll.animate().translationY(0).setDuration(300).start();
            }
        }
    }

    private void animateToSub() {
        PropertyValuesHolder mainTranslationHolder = PropertyValuesHolder.ofFloat("translationY", 0, -mMainScroll.getHeight());
        ValueAnimator mainAnimator = ObjectAnimator.ofPropertyValuesHolder(mMainScroll, mainTranslationHolder)
                .setDuration(PAGE_ANIMATION_DURATION);
        PropertyValuesHolder subTranslationHolder = PropertyValuesHolder.ofFloat("translationY", mSubScroll.getHeight(), 0);
        ValueAnimator subAnimator = ObjectAnimator.ofPropertyValuesHolder(mSubScroll, subTranslationHolder).setDuration(PAGE_ANIMATION_DURATION);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mainAnimator, subAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mSubScroll.setVisibility(View.VISIBLE);
                mSubScroll.setScrollY(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMainScroll.setVisibility(View.INVISIBLE);
                mIsLoadingMore = true;
            }
        });
        animatorSet.start();
    }

    private void animateToMain() {
        PropertyValuesHolder mainTranslationHolder = PropertyValuesHolder.ofFloat("translationY", -mMainScroll.getHeight(), 0);
        ValueAnimator mainAnimator = ObjectAnimator.ofPropertyValuesHolder(mMainScroll, mainTranslationHolder);
        PropertyValuesHolder subTranslationHolder = PropertyValuesHolder.ofFloat("translationY", 0, mSubScroll.getHeight());
        ValueAnimator subAnimator = ObjectAnimator.ofPropertyValuesHolder(mSubScroll, subTranslationHolder);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(PAGE_ANIMATION_DURATION);
        animatorSet.playTogether(mainAnimator, subAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mMainScroll.setVisibility(View.VISIBLE);
                mSubScroll.setScrollY(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mSubScroll.setVisibility(View.INVISIBLE);
                mIsLoadingMore = false;
            }
        });
        animatorSet.start();
    }
}
