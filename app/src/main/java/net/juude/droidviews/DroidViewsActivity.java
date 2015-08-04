package net.juude.droidviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.juude.droidviews.animation.AnimationFragment;
import net.juude.droidviews.camera.PictureFragment;
import net.juude.droidviews.dagger.DaggerFragment;
import net.juude.droidviews.databinding.DataBindingFragment;
import net.juude.droidviews.dialog.PopupWindowFragment;
import net.juude.droidviews.drawable.DrawableMultipleViewsFragment;
import net.juude.droidviews.fresco.FrescoFragment;
import net.juude.droidviews.glide.GlideFragment;
import net.juude.droidviews.graphics.CircleViewFragment;
import net.juude.droidviews.graphics.RoundCornerViewGroupFragment;
import net.juude.droidviews.graphics.surface.SurfaceViewFragment;
import net.juude.droidviews.jni.JniFragment;
import net.juude.droidviews.layout.LinearLayoutFragment;
import net.juude.droidviews.memory.MemoryFragment;
import net.juude.droidviews.otto.OttoFragment;
import net.juude.droidviews.rebound.ReboundFragment;
import net.juude.droidviews.rx.RxAndroidFragment;
import net.juude.droidviews.transition.TransitionFragment;
import net.juude.droidviews.video.VideoPlayFragment;
import net.juude.droidviews.volley.VolleyFragment;
import net.juude.droidviews.widget.SpinnersFragment;
import net.juude.droidviews.widget.WebviewFragment;
import net.juude.droidviews.widget.WidgetsFragment;
import net.juude.droidviews.widget.imageview.ImageViewFilterFragment;
import net.juude.droidviews.widget.imageview.ImageViewScaleTypeFragment;
import net.juude.droidviews.widget.listview.ListDemoFragment;
import net.juude.droidviews.widget.recyclerview.RecyclerViewFragment;
import net.juude.droidviews.widget.textview.TextFragment;

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
            GlideFragment.class,
            PictureFragment.class,
            LinearLayoutFragment.class,
            TextFragment.class,
            AnimationFragment.class,
            ListDemoFragment.class,
            VolleyFragment.class,
            PulltoRefreshFragment.class,
            RoundCornerViewGroupFragment.class,
            SurfaceViewFragment.class,
            FrescoFragment.class,
            RecyclerViewFragment.class,
            VideoPlayFragment.class,
            RxAndroidFragment.class,
            SpinnersFragment.class,
            WebviewFragment.class,
            WidgetsFragment.class ,
            CircleViewFragment.class,
            ImageViewScaleTypeFragment.class,
            PopupWindowFragment.class,
            ImageViewFilterFragment.class,
            DaggerFragment.class,
            DataBindingFragment.class,
            JniFragment.class,
            InfoFragment.class,
            TransitionFragment.class,
            ReboundFragment.class,
            DrawableMultipleViewsFragment.class,
            OttoFragment.class,
            MemoryFragment.class
        };
    }
    private Class mDefaultFragment = MemoryFragment.class;

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
