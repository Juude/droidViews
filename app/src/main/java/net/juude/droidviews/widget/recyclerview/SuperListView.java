package net.juude.droidviews.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by juude on 15/5/24.
 */
public class SuperListView extends RecyclerView {

    private LinearLayoutManager mLayoutManager;
    public SuperListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        setAdapter(new Adapter() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                TextView v = new TextView(getContext());
                v.setText("view " + i);
                return new SimpleViewHolder(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public static class SimpleViewHolder extends ViewHolder {
        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
