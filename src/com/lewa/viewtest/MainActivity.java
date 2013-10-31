package com.lewa.viewtest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.Activity;

import com.android.internal.util.XmlUtils;
import com.lewa.viewtest.mock.MockUtils;

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
    @Override
    protected void onStart() {
        List mHistories;

        try {
            mHistories = XmlUtils.readListXml(this.openFileInput(HISTORY_FILE));
        }
        catch (Exception e) {
            mHistories = new ArrayList<String>();
            e.printStackTrace();
        }
        mHistories.add("com.lewa.viewtest.camera.PictureFragment");
        mHistories.add("com.lewa.viewtest.audio.AudioRecordTest");
        mAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, mHistories);
        mHistoryList.setAdapter(mAdapter);
        final List  histories = mHistories;
        mHistoryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                MockUtils.startFragment(MainActivity.this, histories.get(position).toString());
            }
        });
        super.onStart();
    }
    
}
