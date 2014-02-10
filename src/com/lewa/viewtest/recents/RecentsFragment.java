package com.lewa.viewtest.recents;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManagerNative;
import android.app.Fragment;
import android.app.IActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Process;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RecentsFragment extends Fragment{

    private ListView mListView;
    private Activity mActivity;
    private ArrayList<Bitmap> mList = new ArrayList<Bitmap>();
    private IActivityManager mAm;
    private ThumbnailAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mAdapter = new ThumbnailAdapter();
        mAm = ActivityManagerNative.getDefault();

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();
        try {
            List<ActivityManager.RecentTaskInfo> recentTasks = null;
            ArrayList<Integer> uids = new ArrayList<Integer>();
            final int MAX_TASK_COUNT = 100;
            if(Build.VERSION.SDK_INT < 17) {
                recentTasks = (List<ActivityManager.RecentTaskInfo>)mAm.getClass().getMethod("getRecentTasks", int.class, int.class)
                        .invoke(mAm, MAX_TASK_COUNT, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
            }
            else
            {
                Class<?> classHandle = Class.forName("android.os.UserHandle");
                int usrId = (Integer) classHandle.getMethod("myUserId").invoke(classHandle);
                recentTasks = (List<ActivityManager.RecentTaskInfo>)mAm.getClass().getMethod("getRecentTasks", int.class, int.class,  int.class)
                                .invoke(mAm, MAX_TASK_COUNT, ActivityManager.RECENT_IGNORE_UNAVAILABLE, usrId);
            }
            for(RecentTaskInfo info : recentTasks) {
                uids.add(info.persistentId);
            }
            final LinkedBlockingQueue<Integer> tasksWaitingForThumbnails =
                    new LinkedBlockingQueue<Integer>(uids);
            loadThumbnailsAndIconsInBackground(tasksWaitingForThumbnails);
        }
        catch (Exception e) {
            e.printStackTrace();
        }    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = new ListView(mActivity);
        mListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        TextView v = new TextView(mActivity);
        v.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        v.setTextSize(30);
        v.setText("Empty");
        mListView.setEmptyView(v);
        
        mListView.setAdapter(mAdapter);
        return mListView;
    }
    
    private void loadThumbnailsAndIconsInBackground(
            final BlockingQueue<Integer> tasksWaitingForThumbnails) {

        AsyncTask<Void, Bitmap, Void> mThumbnailLoader = new AsyncTask<Void, Bitmap , Void>() {
            @Override
            protected void onProgressUpdate(Bitmap ...values) {
                mList.add((Bitmap)values[0]);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... params) {
                final int origPri = Process.getThreadPriority(Process.myTid());
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    int id = 0;
                    while (id == 0) {
                        try {
                            id = tasksWaitingForThumbnails.take();
                        } catch (InterruptedException e) {
                        }
                    }
                    if (id == 0) {
                        break;
                    }
                    Bitmap bitmap = loadThumbnailAndIcon(id);
                    publishProgress(bitmap);
                }

                //Process.setThreadPriority(origPri);
                return null;
            }
        };
        mThumbnailLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    
    Bitmap loadThumbnailAndIcon(int persistId) {
        final ActivityManager am = (ActivityManager)
                mActivity.getSystemService(Context.ACTIVITY_SERVICE);        
        ActivityManager.TaskThumbnails thumbs = am.getTaskThumbnails(persistId);
        return thumbs.mainThumbnail;
    }
    
    class ThumbnailAdapter extends BaseAdapter {

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
            Bitmap bitmap =  mList.get(position);
            ImageView view = new ImageView(mActivity);
            view.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            view.setImageBitmap(bitmap);
            return view;
        }
        
    }
    
}
