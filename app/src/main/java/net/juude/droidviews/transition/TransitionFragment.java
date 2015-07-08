package net.juude.droidviews.transition;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-7-7.
 */
public class TransitionFragment extends Fragment{
    private View mParentView;
    private Scene mScene1;
    private Scene mScene2;
    private Scene mCurrentScene;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transition, null);
        mParentView = v;
        mScene1 = new Scene((ViewGroup) mParentView, v.findViewById(R.id.transition));
        mScene2 = Scene.getSceneForLayout((ViewGroup) mParentView, R.layout.transition_right, getActivity());
        mCurrentScene = mScene1;
        v.setOnClickListener(v1 -> {
            if(mCurrentScene == mScene1) {
                transitionTo(mScene2);
            }else {
                transitionTo(mScene1);
            }
        });
        return v;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void transitionTo(Scene scene) {
        mCurrentScene = scene;
        TransitionManager.go(scene);
    }
}
