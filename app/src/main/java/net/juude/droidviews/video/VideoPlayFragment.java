package net.juude.droidviews.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.juude.droidviews.R;

/**
 * Created by juude on 15/5/24.
 */
public class VideoPlayFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, null);
        WebView video_view = (WebView) v.findViewById(R.id.video_view);
        video_view.getSettings().setJavaScriptEnabled(true);
        video_view.loadUrl("http://player.youku.com/embed/XOTYyNTU0MjYw");
        //video_view.loadData("<iframe height=498 width=510 src=\"http://player.youku.com/embed/XOTYyNTU0MjYw\" frameborder=0 allowfullscreen></iframe>",
        //        "text/html; charset=UTF-8",
        //        null);
        return v;
    }
}
