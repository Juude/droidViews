
package net.juude.widgetsdemos.textview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import net.juude.widgetsdemos.R;

import top.perf.utils.text.SmarterSpannableBuilder;

/**
 * shows how to use TextViews.
 * */
public class TextFragment extends Fragment {
    public static final String TAG = TextFragment.class.getSimpleName();
    public final static boolean DEBUG = true;
    private EditText mEditDemo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text, null);
        TextView spannableString = (TextView) v.findViewById(R.id.spannableString);
        SpannableString msp = new SpannableString("回复 一下你么");
        msp.setSpan(new TextAppearanceSpan(getActivity(), R.style.MainText), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new TextAppearanceSpan(getActivity(), R.style.SubText), 2, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setText(msp);

        TextView smarterSpannable = (TextView)v.findViewById(R.id.smarterSpannable);
        SpannableString smarterString = new SmarterSpannableBuilder()
                                        .append("你好",new TextAppearanceSpan(getActivity(), R.style.MainText))
                                        .append("红色的字体",new ForegroundColorSpan(Color.RED))
                                        .append("SubTest", new TextAppearanceSpan(getActivity(), R.style.SubText))
                                        .build();
        smarterSpannable.setText(smarterString);
        mEditDemo = (EditText) v.findViewById(R.id.edit_test);
        initEditDemo();
        initMultilineText(v);
        return v;
    }

    private void initMultilineText(View parentView) {
//        HorizontalScrollView scrollView = (HorizontalScrollView) parentView.findViewById(R.id.scrollView);
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        scrollView.setEnabled(false);
    }

    private void initEditDemo() {
        mEditDemo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                Log.d(TAG, "onKey: " + keyCode);
                return false;
            }
        });
        mEditDemo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "onEditorAction: " + actionId);
                return false;
            }
        });
        mEditDemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged sequence: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged sequence: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged sequence: " + s);
            }
        });
    }

}
