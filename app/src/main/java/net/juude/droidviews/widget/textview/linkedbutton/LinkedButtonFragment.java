
package net.juude.droidviews.widget.textview.linkedbutton;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.juude.droidviews.R;

public class LinkedButtonFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.linked_button_activity, null);
        final Button button1 = (Button) v.findViewById(R.id.button1);

        button1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button1.setTextColor(getResources().getColor(android.R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        button1.setTextColor(getResources().getColor(
                                android.R.color.holo_blue_light));
                        break;
                }
                return false;
            }

        });

        final TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setTextSize(50);
        textView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("action down...");

                        SpannableStringBuilder style = new SpannableStringBuilder(textView
                                .getText());
                        style.setSpan(
                                new BackgroundColorSpan(getResources().getColor(
                                        android.R.color.holo_blue_light)),
                                0,
                                textView.length(),
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        textView.setText(style);
                        textView.setTextColor(getResources().getColor(android.R.color.white));

                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("action up...");
                        SpannableStringBuilder stylex = new SpannableStringBuilder(textView
                                .getText());
                        stylex.setSpan(
                                new BackgroundColorSpan(getResources().getColor(
                                        android.R.color.transparent)),
                                0,
                                textView.length(),
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        textView.setTextColor(getResources().getColor(
                                android.R.color.holo_blue_light));
                        textView.setText(stylex);
                        break;
                    default:
                        System.out.println("action :" + event.getAction());
                }

                return true;
            }

        });
        return v;
    }

}
