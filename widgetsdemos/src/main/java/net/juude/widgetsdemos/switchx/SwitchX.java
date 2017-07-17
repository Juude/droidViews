package net.juude.widgetsdemos.switchx;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import net.juude.widgetsdemos.R;

/**
 * Created by sjd on 2017/7/12.
 */

public class SwitchX extends ViewGroup implements View.OnClickListener {


    private View mSwitchInner;
    private View mMaskView;

    public SwitchX(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_switchx, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSwitchInner = findViewById(R.id.switch_inner);
        mMaskView = findViewById(R.id.view_mask);
        mMaskView.setOnClickListener(this);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mMaskView == null || mSwitchInner == null) {
            return;
        }
        mSwitchInner.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mMaskView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    //only support wrap_content
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaskView == null || mSwitchInner == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if ((widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) &&
            (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {
            mSwitchInner.measure(
                    MeasureSpec.makeMeasureSpec(widthSize, widthMode),
                    MeasureSpec.makeMeasureSpec(heightSize, heightMode)
                            );
            mMaskView.measure(
                    MeasureSpec.makeMeasureSpec(mSwitchInner.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mSwitchInner.getMeasuredHeight(), MeasureSpec.EXACTLY));
            setMeasuredDimension(mSwitchInner.getMeasuredWidth(), mSwitchInner.getMeasuredHeight());
        } else {
            throw new RuntimeException("only support wrap_content for now");
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("requesting switchx change");
    }
}
