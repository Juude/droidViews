package net.juude.widgetsdemos.design.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.juude.widgetsdemos.R;

import top.perf.utils.UIUtils;

/**
 * Created by juude on 2016/12/2.
 */

public class SwitchAppBarBehavior extends CoordinatorLayout.Behavior{

    private NestedScrollView mMainScroll;
    private int mOffsetToLoadMore;
    private int OFFSET_TO_LOAD_MORE = 50;
    private boolean mIsLoadingMore = false;
    private static final int PAGE_ANIMATION_DURATION = 500;

    private boolean mDetailsVisible = false;
    public final static String TAG = "SwitchAppBarBehavior";

    private NestedScrollView mSubScroll;
    private View mAppBar;

    public SwitchAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOffsetToLoadMore =  UIUtils.convertDpToPx(context, OFFSET_TO_LOAD_MORE);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        NestedScrollView scrollView = mIsLoadingMore ? subScroll(coordinatorLayout) : mainScroll(coordinatorLayout);
        if (!mIsLoadingMore) {
            onMainUnconsumed(child, scrollView, dyUnconsumed);
            return;
        }
        int translationY = (int) (scrollView.getTranslationY() - dyUnconsumed/3);
        if (mIsLoadingMore && translationY < 0 || !mIsLoadingMore && translationY > 0) {
            translationY = 0;
        }
        scrollView.setTranslationY(translationY);
    }

    private void onMainUnconsumed(View appBar, NestedScrollView mainScroll, int dyUnconsumed) {
        int oldTranslationY = (int) mainScroll.getTranslationY();

        if (dyUnconsumed < 0) {
            int translationY = oldTranslationY - dyUnconsumed;
            if (translationY > UIUtils.convertDpToPx(appBar.getContext(), 100)) {
                translationY = UIUtils.convertDpToPx(appBar.getContext(), 100);
            }
            mainScroll.setTranslationY(translationY);
            float scale = (translationY + appBar.getHeight()) * 1.0f / appBar.getHeight();
            appBar.setScaleY(scale);
            appBar.setScaleX(scale);
        } else if (dyUnconsumed > 0) {
            int translationY = (oldTranslationY - dyUnconsumed/3);
            if (translationY > 0) {
                translationY = 0;
            }
            appBar.setTranslationY(translationY);
            mainScroll.setTranslationY(translationY);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
//        if (mIsLoadingMore) {
//            return false;
//        } else {
//            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//        if (mIsLoadingMore) {
//            return false;
//        } else {
//            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    private View appbar(CoordinatorLayout parent) {
        if (mAppBar == null) {
            mAppBar = parent.findViewById(R.id.appbar);
        }
        return mAppBar;
    }

    private NestedScrollView mainScroll(CoordinatorLayout parent) {
        if (mMainScroll == null) {
            mAppBar = appbar(parent);
            mMainScroll = (NestedScrollView) parent.findViewById(R.id.main_scroll);
            mMainScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.d(TAG, "main scroll " + v.getScrollY());
                    mAppBar.setPadding(
                            mAppBar.getPaddingLeft(),
                            mAppBar.getPaddingTop(),
                            mAppBar.getPaddingRight(),
                            v.getScrollY()
                    );
                }
            });
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
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View abl, View target) {
        super.onStopNestedScroll(coordinatorLayout, abl, target);
        mAppBar = appbar(coordinatorLayout);
        mMainScroll = mainScroll(coordinatorLayout);
        if (mAppBar.getScaleX() > 1.0f) {
            mAppBar.animate().scaleY(1.0f).scaleX(1.0f).setDuration(300).start();
            mMainScroll.animate().translationY(0).setDuration(300).start();
            return;
        }
        mSubScroll = subScroll(coordinatorLayout);
        if (mIsLoadingMore) {
            if (mSubScroll.getTranslationY() > mOffsetToLoadMore) {
                Log.d(TAG, "jump");
                animateToMain();
            } else {
                mSubScroll.animate().translationY(0).setDuration(300).start();
            }
        } else {
            if (mMainScroll.getTranslationY() < -mOffsetToLoadMore) {
                Log.d(TAG, "jump");
                animateToSub();
            } else {
                mAppBar.animate().translationY(0).setDuration(300).start();
                mMainScroll.animate().translationY(0).setDuration(300).start();
            }
        }
    }

    private void animateToSub() {
        PropertyValuesHolder mainTranslationHolder = PropertyValuesHolder.ofFloat("translationY", 0, -mMainScroll.getHeight());
        ValueAnimator mainAnimator = ObjectAnimator.ofPropertyValuesHolder(mMainScroll, mainTranslationHolder)
                .setDuration(PAGE_ANIMATION_DURATION);
        ValueAnimator appBarAnimator = ObjectAnimator.ofPropertyValuesHolder(mAppBar, mainTranslationHolder)
                .setDuration(PAGE_ANIMATION_DURATION);
        PropertyValuesHolder subTranslationHolder = PropertyValuesHolder.ofFloat("translationY", mSubScroll.getHeight(), 0);
        ValueAnimator subAnimator = ObjectAnimator.ofPropertyValuesHolder(mSubScroll, subTranslationHolder).setDuration(PAGE_ANIMATION_DURATION);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(appBarAnimator, mainAnimator, subAnimator);
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
        ValueAnimator appBarAnimator = ObjectAnimator.ofPropertyValuesHolder(mAppBar, mainTranslationHolder)
                .setDuration(PAGE_ANIMATION_DURATION);
        PropertyValuesHolder subTranslationHolder = PropertyValuesHolder.ofFloat("translationY", 0, mSubScroll.getHeight());
        ValueAnimator subAnimator = ObjectAnimator.ofPropertyValuesHolder(mSubScroll, subTranslationHolder);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(PAGE_ANIMATION_DURATION);
        animatorSet.playTogether(appBarAnimator, mainAnimator, subAnimator);
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
