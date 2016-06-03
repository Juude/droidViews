
package net.juude.widgetsdemos.textview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.juude.droidviews.R;

public class TextFragment extends Fragment {
    public static final String TAG = TextFragment.class.getSimpleName();
    public final static boolean DEBUG = true;

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
                                        .build();
        smarterSpannable.setText(smarterString);

        return v;
    }

}
