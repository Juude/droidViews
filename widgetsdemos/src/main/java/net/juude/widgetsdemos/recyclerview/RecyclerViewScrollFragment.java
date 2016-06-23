package net.juude.widgetsdemos.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.juude.widgetsdemos.R;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by juude on 15/5/24.
 */
public class RecyclerViewScrollFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static int[] sColors = new int[] {
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.YELLOW
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_recycler_in_scrollview, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                frameLayout.setPadding(0, 0, 100, 0);
                TextView textView = new TextView(getContext());
                textView.setTag("textView");
                frameLayout.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
                return new RecyclerView.ViewHolder(frameLayout) {

                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                int i = new Random().nextInt(5);
                holder.itemView.setBackgroundColor(sColors[i]);
                TextView positionView = (TextView) holder.itemView.findViewWithTag("textView");
                positionView.setText("" + position);
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("juude", " child count : " +  ((ViewGroup)v).getChildCount());
            }
        });
        return v;
    }
}
