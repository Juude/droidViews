package net.juude.viewdemos.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import net.juude.viewdemos.R;

public class SeekbarFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.seekbar, null);
        SeekBar seekBar = (SeekBar)v.findViewById(R.id.seekbar);
        seekBar.setMax(100);
        seekBar.setProgress(30);
        return v;
    }    
}
