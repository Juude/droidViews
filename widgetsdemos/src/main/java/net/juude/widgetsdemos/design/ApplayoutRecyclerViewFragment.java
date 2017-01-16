package net.juude.widgetsdemos.design;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import net.juude.widgetsdemos.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by juude on 2016/11/16.
 */
public class ApplayoutRecyclerViewFragment extends Fragment implements OnMoreListener {

    private UsersAdapter mUsersAdapter;
    private OkHttpClient mOkHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_headbar_paralell, container, false);
        SuperRecyclerView recyclerView = (SuperRecyclerView) v.findViewById(R.id.super_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUsersAdapter = new UsersAdapter();
        recyclerView.setAdapter(mUsersAdapter);
        recyclerView.setupMoreListener(this, 5);
        requestUsersData(0);
        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar);
        appBarLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mUsersAdapter.onNewDatas(null);
            }
        });
        return v;
    }

    private void requestUsersData(int last) {
        Observable.range(last + 1, 10)
                   .delay(5, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .toList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                mUsersAdapter.onNewDatas(integers);
            }
        });
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        Log.d("AppScroll",
                "current: " + currentItemPos +
                "\tnumberOfItems : " + numberOfItems +
                 "\tnumberBeforeMore" + numberBeforeMore);
        requestUsersData(numberOfItems);
    }

    public static class UsersAdapter extends RecyclerView.Adapter {

        private ArrayList<Integer> mIntegers = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            TextView textView = new TextView(parent.getContext());
            textView.setPadding(100, 100, 40, 100);
            textView.setBackgroundColor(new Random().nextInt(0xFFFFFF));
            return new RecyclerView.ViewHolder(textView) {

            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText("text " + mIntegers.get(position));
            textView.setBackgroundColor(position % 3 == 0 ? Color.RED : (position % 3 == 1 ? Color.BLUE : Color.GREEN));
        }

        @Override
        public int getItemCount() {
            return mIntegers.size();
        }

        public void onNewDatas(List<Integer> integers) {
            if (integers != null) {
                mIntegers.addAll(integers);
            } else {
                mIntegers.clear();
            }
            notifyDataSetChanged();
        }
    }
}
