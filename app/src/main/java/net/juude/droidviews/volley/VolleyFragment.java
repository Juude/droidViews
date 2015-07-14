package net.juude.droidviews.volley;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.juude.droidviews.R;


/**
 * Created by juude on 15/5/5.
 */
public class VolleyFragment extends Fragment implements View.OnClickListener {
    private RequestQueue mRequestQueue;
    private  StringRequest mStringRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_volley, null);
        v.findViewById(R.id.string_request).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.string_request:
                CharSequence url = ((TextView)getView().findViewById(R.id.edit_url)).getText();
                mStringRequest = new StringRequest(url.toString(), response -> setViewText(R.id.result, response),
                        error -> setViewText(R.id.result, "请求失败"+ error.getMessage() + error.getCause())
                );
                mRequestQueue.add(mStringRequest);
                break;
        }
    }

    private void setViewText(int resId, CharSequence text) {
        ((TextView)getView().findViewById(resId)).setText(text);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mStringRequest != null) {
            mStringRequest.cancel();
        }
    }
}
