package net.juude.imagedemos.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.juude.imagedemos.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by juude on 2016/12/5.
 */

public class ImageViewPagerFragment extends Fragment {

    private WebView mWebView;
    ImageGalleryDelegate mImageGalleryHelper;
    private ArrayList<String> mImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_gallery, container, false);
        mImageGalleryHelper = new ImageGalleryDelegate((ViewPager) v.findViewById(R.id.image_gallery));
        mWebView = (WebView)v.findViewById(R.id.webview_article);
        mWebView.loadUrl("http://gank.io/api");
        retriveImages();
        return v;
    }

    private void retriveImages() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient client = new OkHttpClient();
                try {
                    String result = client.newCall(
                            new Request.Builder()
                                    .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1")
                                    .build())
                            .execute()
                            .body()
                            .string();
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<String, ArrayList<String>>() {
            @Override
            public ArrayList<String> call(String s) {
                try {
                    JSONArray jsonArray = JSON.parseObject(s).getJSONArray("results");
                    List<String> urls = null;
                    ArrayList<String> list = new ArrayList<>();
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            list.add(jsonArray.getJSONObject(i).getString("url"));
                        }
                    }
                    return list;
                } catch (Exception e) {
                    throw Exceptions.propagate(e);
                }
            }
        }).subscribe(new Subscriber<ArrayList<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<String> images) {
                mImages = images;
                mImageGalleryHelper.bind(images);
            }
        });
    }




}
