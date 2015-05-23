package net.juude.droidviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by juude on 15/5/23.
 */
public class PulltoRefreshFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pull_to_refresh, null);
        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) v;
        StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0, 60, 0, 60);
        header.initWithString("Ultra PTR");

        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        return v;

    }
}
