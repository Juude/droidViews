package com.juude.viewdemos.view;

import com.juude.viewdemos.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneClickMultiTag extends Fragment implements View.OnLongClickListener{

    protected static final String TAG = "OneClickMultiTag";
    

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.oneclick_multitag, null);
        return view;
    }



    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        
        return false;
    }  

}
