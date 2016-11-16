package net.juude.imagedemos.glide;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.juude.droidviews.R;
import net.juude.droidviews.utils.LLog;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by juude on 15-7-10.
 */
public class GlideFragment extends Fragment{
    private static final String TAG = "GlideFragment";

    private ImageView mGlideImage;
    private Object callback;
    private ArrayList<String> mImages = new ArrayList<>();
    private RecyclerView mRecyclerImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glide, null);
        mRecyclerImages  = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerImages.setLayoutManager(linearLayoutManager);
        mRecyclerImages.setAdapter(new ImageViewAdapter());
        return v;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private  TextView image_url;
        private  ImageView image_view;
        public ImageViewHolder(View itemView, int viewType) {
            super(itemView);
            if(viewType == TYPE_HEADER) {
                mGlideImage = (ImageView) itemView.findViewById(R.id.glide_image_url);
                Glide.with(getActivity())
                        .load("https://raw.githubusercontent.com/JuudeDemos/WebDisk/master/food/IMG_0693.PNG")
                        .fitCenter()
                        .placeholder(R.drawable.ic_action_grade)
                        .into(mGlideImage);

                ImageView mGlideImageRes = (ImageView) itemView.findViewById(R.id.glide_image_res);
                Glide.with(getActivity())
                        .load("android.resource://net.juude.droidviews/drawable/juude")
                        .fitCenter()
                        .placeholder(R.drawable.ic_action_grade)
                        .into(mGlideImageRes);

                ImageView mGlideImageAssets = (ImageView) itemView.findViewById(R.id.glide_image_assets);
                Glide.with(getActivity())
                        .load("android_asset://download.png")
                        .fitCenter()
                        .placeholder(R.drawable.ic_action_grade)
                        .into(mGlideImageAssets);
            }else {
                image_url = (TextView) itemView.findViewById(R.id.image_url);
                image_view = (ImageView) itemView.findViewById(R.id.image_view);
            }
        }
    }
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BODY = 1;

    private class ImageViewAdapter extends RecyclerView.Adapter<ImageViewHolder>{

        @Override
        public int getItemViewType(int position) {
            if(position == 0) {
                return TYPE_HEADER;
            }else {
                return TYPE_BODY;
            }
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == 0) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_glide_header, parent, false);
                return new ImageViewHolder(v, viewType);
            }else {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_glide, parent, false);
                return new ImageViewHolder(v, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            if (position == 0) {
                //pass ...
            }else {
                LLog.d(TAG, "img url" + mImages.get(position-1));
                Glide.with(getActivity())
                        .load(Uri.parse(mImages.get(position -1)))
                        .placeholder(R.drawable.ic_action_grade)
                        .fitCenter()
                        .transform(new CircleCrop(getActivity()))
                        .into(holder.image_view);
                holder.image_url.setText(mImages.get(position -1));
            }
        }

        @Override
        public int getItemCount() {
            return mImages.size();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(
                new Request.Builder()
                        .url("https://api.imgur.com/3/gallery.json")
                        .addHeader("Authorization", "Client-ID 4408bab9df4233c")
                        .build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                JSONArray array = JSON.parseObject(response.body().string())
                        .getJSONArray("data");
                        //response.body().string();
                synchronized (mImages) {
                    for(int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        if(object.getBoolean("is_album") == false) {
                            mImages.add(array.getJSONObject(i).getString("link"));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerImages.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
