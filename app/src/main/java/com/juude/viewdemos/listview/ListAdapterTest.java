
package com.juude.viewdemos.listview;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 1.setEmptyView 2.ViewHolder 3.ViewHolder compare 4.
 */

public class ListAdapterTest extends Fragment {
    private Activity mActivity;
    public static String TAG = "LogsFragment";
    private TestAdapter mLogsAdapter;
    private static final int MENU_CLEAR = 1;
    private static final int MENU_ADD = 2;
    private List<String> mList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        mLogsAdapter = new TestAdapter();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        setList();
    }

    private View createEmptyView() {
        View v = new View(mActivity);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        v.setBackgroundColor(Color.RED);
        v.setId(android.R.id.empty);
        return v;
    }

    private void setList() {
        mList.add("f1");
        mList.add("f2");
        mList.add("f3");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_CLEAR, Menu.NONE, "clear logs");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case MENU_CLEAR:
                mList.clear();
                mLogsAdapter.notifyDataSetChanged();
                return true;
            case MENU_ADD:
                setList();
                mLogsAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout layout = new FrameLayout(mActivity);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        ListView listView = new ListView(mActivity);
        listView.setAdapter(mLogsAdapter);
        View emptyView = createEmptyView();
        listView.setEmptyView(emptyView);
        layout.addView(listView);
        layout.addView(emptyView);
        return layout;
    }

    private class TestAdapter extends BaseAdapter {
        public TestAdapter() {

        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = new TextView(mActivity);
            v.setText(mList.get(position));
            return v;
        }
    }

}
