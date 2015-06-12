package net.juude.droidviews.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-6-12.
 */
public class DataBindingActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        net.juude.droidviews.databinding.ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User();
        user.setSex("双性");
        user.setName("斌佳");
        binding.setUser(user);
    }


}
