package net.juude.droidviews.debug;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.juuda.droidmock.mock.Mocker;
import net.juude.droidviews.mock.FragmentMocker;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ViewDeugMocker extends Mocker{
    
    public static final String TAG = "ViewDeugMocker";
    
    public ViewDeugMocker(Context context, Bundle extras) {
        super(context, extras);
    }
    
    @Override
    public void dump() {
        View v = new LinearLayout(mContext);
        ArrayList<View> dumpViews = new ArrayList<View>();
        dumpViews.add(v);
        boolean skip = (FragmentMocker.getInt(mExtras, "skip", 1) == 1);
        boolean prop = (FragmentMocker.getInt(mExtras, "prop", 1) == 1);

        String id = mExtras.getString("id", null);
        
        if(id != null) {
            int viewId = mContext.getResources().getIdentifier(id, "id", "net.juude.viewdemos");
            dumpViews.clear();
            findViewsById(v, viewId , dumpViews);
        }
        
        String tag = mExtras.getString("tag", null);
        if(tag != null) {
            dumpViews.clear();
            findViewsByTag(v, tag , dumpViews);
        }
        
        String kclass = mExtras.getString("class", null);
        if(kclass != null) {
            dumpViews.clear();
            findViewsByClass(v, kclass , dumpViews);
        }
        for(View toDump : dumpViews) {
            Log.e(TAG, "dumping view " + toDump);
            try {
                if(toDump instanceof ViewGroup) {
                    Method dumpView = ViewDebug.class.getDeclaredMethod("dumpViewHierarchy", 
                            Context.class, ViewGroup.class, BufferedWriter.class, int.class, boolean.class, boolean.class);
                    dumpView.setAccessible(true);
                    dumpView.invoke(ViewDebug.class, mContext, (ViewGroup)toDump, 
                            new BufferedWriter(new PrintWriter(System.out)),
                            0, skip, prop);  
                }
                else {
                    Method dumpView = ViewDebug.class.getDeclaredMethod("dumpView",
                            Context.class, View.class, BufferedWriter.class, int.class, boolean.class);
                    dumpView.setAccessible(true);
                    dumpView.invoke(ViewDebug.class, mContext, toDump, 
                            new BufferedWriter(new PrintWriter(System.out)),
                            0, prop); 
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void findViewsByClass(View v, String className, List<View> result) {
        if(v.getClass().getName().contains(className)) {
            result.add(v);
        }
        if(v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)v;
            for(int i = 0; i < vg.getChildCount(); i++) {
                findViewsByClass(vg.getChildAt(i), className, result);
            }
        }
    }
    
    public void findViewsById(View v, int id, List<View> result) {
        if(v.getId() == id) {
            result.add(v);
        }
        if(v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)v;
            for(int i = 0; i < vg.getChildCount(); i++) {
                findViewsById(vg.getChildAt(i), id, result);
            }
        }
    }

    private boolean objectsEqual(Object a, Object b) {
        return a == b || a.equals(b);
    }

    public void findViewsByTag(View v, Object tag, List<View> result) {
        if(objectsEqual(tag, v.getTag())) {
            result.add(v);
        }
        if(v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)v;
            for(int i = 0; i < vg.getChildCount(); i++) {
                findViewsByTag(vg.getChildAt(i), tag, result);
            }
        }
    }
}
