package net.juude.viewdemos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.juude.viewdemos.R;

public class UpdateViewLayout extends RelativeLayout{

    public UpdateViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        updateViewLayout(findViewById(R.id.text), new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 
                ViewGroup.LayoutParams.WRAP_CONTENT));
        super.onAttachedToWindow();
    }
    
    

}
