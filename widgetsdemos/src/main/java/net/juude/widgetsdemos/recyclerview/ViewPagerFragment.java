package net.juude.widgetsdemos.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.juude.widgetsdemos.R;

/**
 * Created by juude on 15/5/24.
 */
public class ViewPagerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager, null);
        final ViewPager view_pager = (ViewPager) v.findViewById(R.id.view_pager);
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView textView = new TextView(getActivity());
                textView.setText("PAGE " + position);
                container.addView(textView);
                return textView;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

        });
        return v;
    }
}
