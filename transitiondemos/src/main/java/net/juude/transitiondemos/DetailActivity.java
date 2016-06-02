package net.juude.transitiondemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by sjd on 16/6/1.
 */
public class DetailActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String imagePath = getIntent().getStringExtra("path");
        ImageView imageView = new ImageView(this);
        imageView.setTransitionName(imagePath);
        Picasso.with(this).load(imagePath).into(imageView);
        setContentView(imageView);
    }
}
