
package net.juude.droidviews.camera;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.juude.droidviews.R;

public class PictureFragment extends Fragment {
    private SurfaceHolder mSurfaceHolder = null;
    private PictureUtils mPictureUtils;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SurfaceView mSurfaceView = new SurfaceView(getActivity());
        View v = inflater.inflate(R.layout.picture_activity, null);
        mSurfaceView = (SurfaceView) v.findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mPictureUtils = new PictureUtils(mSurfaceHolder);
        Button button = (Button)v.findViewById(R.id.takePhoto);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                takePhoto();
            }
        });
        super.onCreate(savedInstanceState);
        return v;
    }

    public void takePhoto()
    {
        mPictureUtils.takePicture();
    }

}
