package com.lewa.viewtest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;


public class CommonActivity extends Activity {

    public static final String KEY_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout parent = new FrameLayout(this);
        parent.setId(android.R.id.content);
        setContentView(parent);
        initActionbar();
        setupFragment();
    }
    
    private void initActionbar(){
        String title = getIntent().getStringExtra(Intent.EXTRA_TITLE);
        if(title != null)
            setTitle(title);
    }
    
    private void setupFragment(){
        try {
            String clazz = getIntent().getStringExtra(KEY_FRAGMENT);
            Fragment fragment = (Fragment)Class.forName(clazz).newInstance();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
        } catch (Exception e) {
            finish();
            return;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
