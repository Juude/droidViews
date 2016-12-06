package net.juude.widgetsdemos.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.juude.widgetsdemos.R;
/**
 * Created by juude on 15-5-25.
 * this demo will pop up window when click
 */
public class PopupWindowFragment extends Fragment{
    private static final String TAG = "PopupWindowFragment";
    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_popup, null);
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        v.findViewById(R.id.toggleBottomDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View popUp = View.inflate(getActivity(), R.layout.layout_popup, null);
                popUp.setVisibility(View.VISIBLE);
                final PopupWindow popupWindow = PopupWindowCreator.create(popUp);
                popupWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
                View main_area = popUp.findViewById(R.id.main_area);
                main_area.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "main show", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setElevation(5);
        mToolbar.showOverflowMenu();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(10);
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity)getActivity()).setSupportActionBar(null);
    }
}
