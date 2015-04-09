package net.juude.droidviews.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import net.juude.droidviews.R;

public class TestIncludeView extends RelativeLayout{

    public TestIncludeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        
        //findViewById(com.lewa.viewtest.R.id.include).setBackgroundColor(Color.RED);
        findViewById(R.id.included).setBackgroundColor(Color.GREEN);

        super.onFinishInflate();
    }
    

    

}
