package net.juude.droidviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

import static net.juude.droidviews.R.drawable.gradient;

/**
 * Created by juude on 15/5/23.
 */
public class PulltoRefreshFragment extends Fragment{
    private static final String TAG = "PulltoRefreshFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pull_to_refresh, null);
        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) v;
        View header = new View(getActivity());
        header.setBackground(getResources().getDrawable(R.drawable.gradient));
        header.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));

        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.setOffsetToRefresh(300);
        ptrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout ptrFrameLayout) {
                Log.d(TAG, "onUIReset : " );
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
                Log.d(TAG, "onUIRefreshPrepare : " );
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                Log.d(TAG, "onUIRefreshBegin : " );
                ptrFrameLayout.refreshComplete();
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
                Log.d(TAG, "onUIRefreshComplete : " );
            }

            @Override
            public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean b, byte b2, PtrIndicator ptrIndicator) {
                Log.d(TAG, "position : " + ptrIndicator.getCurrentPosY());
            }
        });
        ptrFrameLayout.setPinContent(true);
        return v;

    }
}
