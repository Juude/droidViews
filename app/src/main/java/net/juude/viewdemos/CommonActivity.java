package net.juude.viewdemos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Constructor;


public class CommonActivity extends Activity {

    public static final String KEY_FRAGMENT = "fragment";
    public static final String KEY_VIEW = "view";
    private static final String TAG = "CommonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout parent = new FrameLayout(this);
        parent.setId(android.R.id.content);
        setContentView(parent);
        initActionbar();
        String clazz = getIntent().getStringExtra(KEY_FRAGMENT);
        if(clazz != null) {
            setupFragment();
            return;
        }
        Log.e(TAG, "heool");
        String viewClazz = getIntent().getStringExtra(KEY_VIEW);
        setupView(parent, viewClazz);
        
    }
    
    private void setupView(ViewGroup parent, String viewClazz) {
        Class<?> viewClass;
        try {
            viewClass = Class.forName(viewClazz);
            Constructor<?> viewConstructor = viewClass.getConstructor(Context.class, FrameLayout.LayoutParams.class);
            View v = (View)viewConstructor.newInstance(this, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    ViewGroup.LayoutParams.MATCH_PARENT));
            parent.addView(v);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
