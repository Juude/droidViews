package net.juude.widgetsdemos.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import net.juude.widgetsdemos.R;

import java.util.Random;

/**
 * Created by juude on 2016/11/16.
 */
public class ApplayoutScrollingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_appbar_scrolling, container, false);
        SuperRecyclerView recyclerView = (SuperRecyclerView) v.findViewById(R.id.super_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(getContext());
                textView.setPadding(50, 40, 40, 50);
                textView.setText("text" + new Random().nextInt());
                textView.setBackgroundColor(new Random().nextInt(0xFFFFFF));
                return new RecyclerView.ViewHolder(textView) {

                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
        return v;
    }
}
