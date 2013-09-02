
package com.lewa.viewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lewa.viewtest.R;

public class LinedTextView extends TextView {

    private int mLineSpacing;
    private Bitmap mBitmap;
    private Context mContext;
    private Rect mBounds;
    private float mDensity;
    public String TAG = "LinedTextView";

    public LinedTextView(Context context) {
        this(context, null, 0);
    }

    public LinedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bg_blue);
        mBounds = new Rect();
        mDensity = mContext.getResources().getDisplayMetrics().density;
        mLineSpacing = (int) (40 * mDensity);
        setLineSpacing(mLineSpacing * 2, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < getLineCount(); i++)
        {
            getLineBounds(i, mBounds);
            System.out.println(mBounds);
            System.out.println("bitmap is " + mBitmap);
            mBounds.top = mBounds.bottom - mLineSpacing;
            mBounds.bottom = (int) (mBounds.top + mDensity);
            canvas.drawBitmap(mBitmap, null, mBounds, null);
        }
        super.onDraw(canvas);
    }
}
