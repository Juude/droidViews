package net.juude.droidviews.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-6-9.
 */
public class DaggerFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_dagger, null);
        TextView textView = (TextView) v.findViewById(R.id.dagger_text);
        textView.setText(new SomeThing().getName());
        return v;
    }
}
