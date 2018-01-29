package net.juude.droidviews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.juude.droidviews.graphics.GraphicsDemoFragment;
import net.juude.droidviews.notifications.NotificationSettingsFragment;
import net.juude.droidviews.perf.memory.MemoryFragment;
import net.juude.droidviews.perf.overdraw.OverdrawFragment;
import net.juude.droidviews.surface.SurfaceFragment;
import net.juude.droidviews.video.VideoPlayFragment;
import net.juude.droidviews.view.SystemUiDemoFragment;

import java.util.HashMap;

/**
 * Created by juude on 15-4-9.
 */

public class DroidViewsActivity extends FragmentActivity {

    private static final String TAG = "DroidViewsActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private IndexAdapter mIndexAdapter;
    private LayoutInflater mLayoutInflater;
    private static Class[] sFragmentList;
    private HashMap<Class, Fragment> mFragmentsMap = new HashMap<Class, Fragment>();

    static {
        sFragmentList = new Class<?>[] {
            SystemUiDemoFragment.class,
            VideoPlayFragment.class,
            GraphicsDemoFragment.class,
            MemoryFragment.class,
            SurfaceFragment.class,
            OverdrawFragment.class,
            NotificationSettingsFragment.class
        };
    }
    private Class mDefaultFragment = GraphicsDemoFragment.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droidviews);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mLayoutInflater = LayoutInflater.from(this);

        // Set the adapter for the list view
        mIndexAdapter = new IndexAdapter();
        mDrawerList.setAdapter(mIndexAdapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        selectFragment(mDefaultFragment);
        registerReceiver(mReceiver, new IntentFilter("com.alipay.security.namecertified"));
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "action :" + intent.getAction());
        }
    };

    public void jumpToUrl(String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    private Fragment getFragment(Class clazz) {
        Fragment fragment = mFragmentsMap.get(clazz);
        if(fragment == null) {
            try {
                fragment = (Fragment) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            mFragmentsMap.put(clazz, fragment);
        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void selectFragment(Class clazz) {
        Fragment fragment = getFragment(clazz);
        getSupportFragmentManager().beginTransaction().
            replace(R.id.content_frame, fragment).
            commit();
        setTitle(clazz.getSimpleName());
    }

    static class IndexItemViewHolder {
        TextView mTitleView;
    }

    class IndexAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sFragmentList.length;
        }

        @Override
        public Object getItem(int i) {
            return sFragmentList[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            IndexItemViewHolder holder = (IndexItemViewHolder) convertView.getTag();
            if(holder == null) {
                TextView titleView = (TextView)convertView.findViewById(android.R.id.text1);
                holder = new IndexItemViewHolder();
                holder.mTitleView = titleView;
                titleView.setTag(holder);
            }
            holder.mTitleView.setText(((Class)getItem(i)).getSimpleName());
            return convertView;
        }
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectFragment(sFragmentList[i]);
            mDrawerLayout.closeDrawers();
        }
    }
}
