package net.juude.droidviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.LinearLayout;

import net.juude.droidviews.R;

public class ViewStubContainer extends LinearLayout{

    public ViewStubContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public ViewStubContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public ViewStubContainer(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onFinishInflate() {
        ViewStub viewStub = (ViewStub)findViewById(R.id.viewStub);
        viewStub.inflate();
        super.onFinishInflate();
    }
}
