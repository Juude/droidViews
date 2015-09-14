package net.juude.droidviews.surface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-8-21.
 */
public class SurfaceFragment extends Fragment{
    private DemoGLSurfaceView mDemoGLSurfaceView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_surface, null);
        mDemoGLSurfaceView = (DemoGLSurfaceView) v.findViewById(R.id.demoGLSurfaceView);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDemoGLSurfaceView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDemoGLSurfaceView.onPause();
    }
}
