package top.perf.utils.text;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;

import java.util.ArrayList;

/**
 * Created by juude on 15-5-11.
 */
public class SmarterSpannableBuilder {
    private ArrayList<Object> mSpans = new ArrayList<>();
    private ArrayList<Integer> mEndIndexes = new ArrayList<>();
    private int mCurrentEndIndex = 0;
    private StringBuilder mTexts = new StringBuilder();

    public SmarterSpannableBuilder() {

    }

    public SmarterSpannableBuilder append(@NonNull String text, @NonNull Object span) {
        mSpans.add(span);
        mCurrentEndIndex += text.length();
        mEndIndexes.add(mCurrentEndIndex);
        mTexts.append(text);
        return this;
    }

    public SpannableString build() {
        SpannableString spannableString = new SpannableString(mTexts.toString());
        for(int i =0; i< mSpans.size(); i++) {
            spannableString.setSpan(mSpans.get(i), i == 0 ? 0 : mEndIndexes.get(i - 1), mEndIndexes.get(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}
