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

public class BottomViewFragment extends Fragment {

    private View mPopupLayout;
    private int mMainAreaHeight;
    private View mBottomPanel;
    private final int ANIMATION_DURATION = 1300;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_popup, container, false);
        View buttonShowDialog = v.findViewById(R.id.buton_show_dialog);
        mMainAreaHeight = UIUtils.convertDpToPx(getContext(), 100);
        mBottomPanel = v.findViewById(R.id.bottom_panel);
        mBottomPanel.setVisibility(View.VISIBLE);
        buttonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupLayout = v.findViewById(R.id.popup);
                if (mPopupLayout.getVisibility() == View.VISIBLE) {
                    animateHide();
                } else {
                    animateShow();
                }
            }
        });
        return v;
    }

    private void animateShow() {
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

    private void animateHide() {
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
