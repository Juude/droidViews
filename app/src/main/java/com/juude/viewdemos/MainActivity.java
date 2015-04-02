package com.juude.viewdemos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.juude.viewdemos.audio.AudioRecordTest;
import com.juude.viewdemos.camera.PictureFragment;
import com.juude.viewdemos.layout.LinearLayoutTest;
import com.juude.viewdemos.mock.FragmentMocker;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String HISTORY_FILE = "history.xml";
    private ListView mHistoryList;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHistoryList = (ListView)findViewById(R.id.historyList);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressWarnings({
            "unchecked", "rawtypes"
    })

    private ArrayList readListXml(FileInputStream fileInputStream)throws Exception{
        Class clazzXmlUtils = Class.forName("com.android.internal.util.XmlUtils");
        Method methodReadListXml = clazzXmlUtils.getDeclaredMethod("readListXml", InputStream.class);
        return (ArrayList)methodReadListXml.invoke(clazzXmlUtils, fileInputStream);
    }

    @Override
    protected void onStart() {
        List mHistories;

        try {
            mHistories = readListXml(this.openFileInput(HISTORY_FILE));
        }
        catch (Exception e) {
            mHistories = new ArrayList<String>();
            e.printStackTrace();
        }
        mHistories.add(PictureFragment.class.getName());
        mHistories.add(AudioRecordTest.class.getName());
        mHistories.add(LinearLayoutTest.class.getName());

        mAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, mHistories);
        mHistoryList.setAdapter(mAdapter);
        final List  histories = mHistories;
        mHistoryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                FragmentMocker.startFragment(MainActivity.this, histories.get(position).toString());
            }
        });
        super.onStart();
    }

    
}
