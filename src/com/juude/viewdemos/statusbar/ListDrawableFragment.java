package com.juude.viewdemos.statusbar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juude.viewdemos.R;
import com.juude.viewdemos.statusbar.TransparentManager.BackgroundState;

import java.util.Random;

public class ListDrawableFragment extends Fragment{

    protected static final String STATE_BACKGROUND_BLACKTAG = "ListDrawableFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.status_bar_test, null);
        View mStatusBarView = v.findViewById(R.id.status_bar);
        
        final TransparentManager manager = 
                new TransparentManager(mStatusBarView, TransparentManager.BackgroundState.STATE_BACKGROUND_BLACK);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int nextLevel = new Random().nextInt(4);
                BackgroundState state = BackgroundState.STATE_BACKGROUND_BLACK;
                switch(nextLevel) {
                    case 0 :
                        state = BackgroundState.STATE_BACKGROUND_BLACK;
                        break;
                    case 1 :
                        state = BackgroundState.STATE_BACKGROUND_KEYGUARD;
                        break;
                    case 2 :
                        state = BackgroundState.STATE_BACKGROUND_TRANSLUCENT;
                        break;
                    case 3 :
                        state = BackgroundState.STATE_BACKGROUND_TRANSPARENT;
                        break;
                }
                manager.setState(state);
         }
        });
        return v;
    }
    
}
