package net.juude.transitiondemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by sjd on 16/6/1.
 */
public class DetailActivity extends AppCompatActivity{
    private static final String TAG = "DetailActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String imagePath = getIntent().getStringExtra("path");
        ImageView imageView = new ImageView(this);
        imageView.setTransitionName(imagePath);
        setContentView(imageView);
        Log.d(TAG, "onCreate begin");
        sleepIgnoreExceptions(getLongExtra("onCreateTime"));
        Log.d(TAG, "onCreate begin");
    }


    private long getLongExtra(String key) {
        return getIntent().getLongExtra(key, 0);
    }

    private void sleepIgnoreExceptions(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart begin");
        sleepIgnoreExceptions(getLongExtra("onStartTime"));
        Log.d(TAG, "onStart end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume begin");
        sleepIgnoreExceptions(getLongExtra("onResumeTime"));
        Log.d(TAG, "onResume end");
    }
}
