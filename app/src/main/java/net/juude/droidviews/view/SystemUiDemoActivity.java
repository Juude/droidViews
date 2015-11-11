package net.juude.droidviews.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.juude.droidviews.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by juude on 15-11-11.
 */
public class SystemUiDemoActivity extends Activity implements CompoundButton.OnCheckedChangeListener{
    private int mSystemUiVisibility = 0;

    @Bind(R.id.toggleSystemUi)
    Button mButtonSystemUi;

    @Bind(R.id.SYSTEM_UI_FLAG_FULLSCREEN)
    CheckBox SYSTEM_UI_FLAG_FULLSCREEN_CheckBox;

    @Bind(R.id.SYSTEM_UI_FLAG_IMMERSIVE)
    CheckBox SYSTEM_UI_FLAG_IMMERSIVE_CheckBox;

    @Bind(R.id.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    CheckBox SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN_CheckBox;

    @Bind(R.id.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    CheckBox SYSTEM_UI_FLAG_LAYOUT_STABLE_CheckBox;


    @Bind(R.id.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    CheckBox SYSTEM_UI_FLAG_IMMERSIVE_STICKY_CheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_systemui_demo);
        ButterKnife.bind(this);
        SYSTEM_UI_FLAG_FULLSCREEN_CheckBox.setOnCheckedChangeListener(this);
        SYSTEM_UI_FLAG_IMMERSIVE_CheckBox.setOnCheckedChangeListener(this);
        SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN_CheckBox.setOnCheckedChangeListener(this);
        SYSTEM_UI_FLAG_LAYOUT_STABLE_CheckBox.setOnCheckedChangeListener(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == SYSTEM_UI_FLAG_FULLSCREEN_CheckBox) {
            if(isChecked) {
                mSystemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }else {
                mSystemUiVisibility &= (~View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }else if(buttonView == SYSTEM_UI_FLAG_IMMERSIVE_CheckBox) {
            if(isChecked) {
                mSystemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE;
            }else {
                mSystemUiVisibility &= (~View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }else if(buttonView == SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN_CheckBox) {
            if(isChecked) {
                mSystemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }else {
                mSystemUiVisibility &= (~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }else if(buttonView == SYSTEM_UI_FLAG_LAYOUT_STABLE_CheckBox) {
            if(isChecked) {
                mSystemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }else {
                mSystemUiVisibility &= (~View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }else if(buttonView == SYSTEM_UI_FLAG_IMMERSIVE_STICKY_CheckBox) {
            if(isChecked) {
                mSystemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }else {
                mSystemUiVisibility &= (~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
        getWindow().getDecorView().setSystemUiVisibility(mSystemUiVisibility);
    }
}
