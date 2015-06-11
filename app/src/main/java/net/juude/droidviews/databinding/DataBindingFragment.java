package net.juude.droidviews.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import net.juude.droidviews.BR;
import net.juude.droidviews.R;

/**
 * Created by juude on 15-6-9.
 */
public class DataBindingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_binding, container, false);
        //dataBinding.setVariable(BR.)
        User user = new User();
        user.setSex("双性");
        user.setName("斌佳");
        //dataBinding.setVariable(BR.user, user);
        return dataBinding.getRoot();
    }
}
