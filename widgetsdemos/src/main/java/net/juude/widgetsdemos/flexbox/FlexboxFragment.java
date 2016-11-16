package net.juude.widgetsdemos.flexbox;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.juude.widgetsdemos.R;

/**
 * Created by juude on 15/5/31.
 */
public class FlexboxFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flexbox, null);
        return v;
    }
}
