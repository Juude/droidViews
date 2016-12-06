package net.juude.widgetsdemos.popup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import net.juude.widgetsdemos.R;

import top.perf.utils.UIUtils;

/**
 * Created by sjd on 2016/12/5.
 */

public class SlideViewFragment extends Fragment {

    private View mPopupLayout;
    private int mMainAreaHeight;
    private View mBottomPanel;
    private final int ANIMATION_DURATION = 300;
    private View mTopDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_popup, container, false);
        View buttonShowDialog = v.findViewById(R.id.toggleBottomDialog);
        mMainAreaHeight = UIUtils.convertDpToPx(getContext(), 100);
        mBottomPanel = v.findViewById(R.id.bottom_panel);
        mBottomPanel.setVisibility(View.VISIBLE);
        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupLayout = v.findViewById(R.id.popup);
                if (mPopupLayout.getVisibility() == View.VISIBLE) {
                    animateHideBottom();
                } else {
                    animateShowBottom();
                }
            }
        });

        View toggleTopDialog = v.findViewById(R.id.toggleTopDialog);
        mTopDialog = v.findViewById(R.id.top_dialog);
        toggleTopDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTopDialog.getVisibility() == View.VISIBLE) {
                    animateHideTop();
                } else {
                    animateShowTop();
                }
            }
        });
        return v;
    }

    private void animateShowTop() {
        mTopDialog.setTranslationY(-mTopDialog.getHeight());
        ViewPropertyAnimator animator = mTopDialog.animate().translationY(0).setDuration(ANIMATION_DURATION);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationEnd(animation);
                mTopDialog.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private void animateHideTop() {
        ViewPropertyAnimator animator = mTopDialog.animate().translationY(-mTopDialog.getHeight()).setDuration(ANIMATION_DURATION);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTopDialog.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private void animateShowBottom() {
        mPopupLayout.setVisibility(View.VISIBLE);
        View mainArea = mPopupLayout.findViewById(R.id.main_area);
        mainArea.setTranslationY(mMainAreaHeight);
        ViewPropertyAnimator animator = mainArea.animate().translationY(0).setDuration(ANIMATION_DURATION);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();
    }

    private void animateHideBottom() {
        View mainArea = mPopupLayout.findViewById(R.id.main_area);
        ViewPropertyAnimator animator = mainArea.animate().translationY(mMainAreaHeight).setDuration(ANIMATION_DURATION);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mPopupLayout.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

}
