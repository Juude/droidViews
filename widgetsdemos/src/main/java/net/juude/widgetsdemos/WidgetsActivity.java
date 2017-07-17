package net.juude.widgetsdemos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.juude.widgetsdemos.alertdialog.AlertDialogFragment;
import net.juude.widgetsdemos.design.appbar.ApplayoutNestScrollingFragment;
import net.juude.widgetsdemos.design.ApplayoutRecyclerViewFragment;
import net.juude.widgetsdemos.design.NestingScrollingFragment;
import net.juude.widgetsdemos.flexbox.FlexboxFragment;
import net.juude.widgetsdemos.imageview.ImageViewFilterFragment;
import net.juude.widgetsdemos.imageview.ImageViewScaleTypeFragment;
import net.juude.widgetsdemos.layout.LinearLayoutFragment;
import net.juude.widgetsdemos.popup.SlideViewFragment;
import net.juude.widgetsdemos.popup.PopupWindowFragment;
import net.juude.widgetsdemos.switchx.SwitchxFragment;
import net.juude.widgetsdemos.viewpager.ImageViewPagerFragment;
import net.juude.widgetsdemos.viewpager.ViewPagerFragment;
import net.juude.widgetsdemos.textview.TextFragment;

import java.util.HashMap;

/**
 * Created by juude on 15-4-9.
 */

public class WidgetsActivity extends AppCompatActivity {

    private static final String TAG = "DroidViewsActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private IndexAdapter mIndexAdapter;
    private LayoutInflater mLayoutInflater;
    private static Class[] sFragmentList;
    private HashMap<Class, Fragment> mFragmentsMap = new HashMap<Class, Fragment>();

    private static Class mDefaultFragment = SwitchxFragment.class;
    static {
        sFragmentList = new Class<?>[] {
            SwitchxFragment.class,
            LinearLayoutFragment.class,
            ApplayoutNestScrollingFragment.class,
            ApplayoutRecyclerViewFragment.class,
            NestingScrollingFragment.class,
            ViewPagerFragment.class,
            TextFragment.class,
            WidgetsFragment.class,
            AlertDialogFragment.class,
            SpinnersFragment.class,
            FlexboxFragment.class,
            ImageViewFilterFragment.class,
            PopupWindowFragment.class,
            SlideViewFragment.class,
            ImageViewScaleTypeFragment.class,
            ImageViewPagerFragment.class,
            WebviewFragment.class
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mLayoutInflater = LayoutInflater.from(this);

        // Set the adapter for the list view
        mIndexAdapter = new IndexAdapter();
        mDrawerList.setAdapter(mIndexAdapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        selectFragment(mDefaultFragment);
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
