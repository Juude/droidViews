package net.juude.droidviews.databinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by juude on 15-6-9.
 */
public class DataBindingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.activity_data_binding, container, false);
//        User user = new User();
//        user.setSex("双性");
//        user.setName("斌佳");
//        dataBinding.setVariable(BR.user, user);
//        dataBinding.executePendingBindings();
//        //dataBinding.setVariable(BR.user, user);
//        return dataBinding.getRoot();
        Button button = new Button(getActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DataBindingActivity.class));
            }
        });
        return button;
    }
}
