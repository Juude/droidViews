package net.juude.testdemos;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by juude on 16/5/18.
 */
public class AnimationDemoFragment extends Fragment{
    private int A = 300;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_animation_demo, null);
        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getAnimator(view.findViewById(R.id.view1)).start();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAnimator(view.findViewById(R.id.view2)).start();
                    }
                }, 50);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAnimator(view.findViewById(R.id.view3)).start();
                    }
                }, 100);
            }
        });
        return view;
    }

    private Animator getAnimator(View v) {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(v, "translationY", 0, A);
        objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator1.setDuration(120);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(v, "translationY", A, -A);
        objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator1.setDuration(410 - 120);

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(v, "translationY", -A, A);
        objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator1.setDuration(650 - 410);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3);
        animatorSet.setDuration(650);
        return animatorSet;
    }
}
