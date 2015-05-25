package net.juude.droidviews.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;

import net.juude.droidviews.R;

/**
 * Created by juude on 15-5-14.
 */
public class WebviewFragment extends Fragment {
    private static final String TAG = "WebviewFragment";
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);
        WebView webView = (WebView) v.findViewById(R.id.webView);
        mWebView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSInterface(webView), "android");
        EditText edit_url = (EditText) v.findViewById(R.id.edit_url);
        String url = "https://github.com/juude";
        edit_url.setText(url);
        webView.loadUrl(url);
        v.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }, 2000);
            }
        });

        v.findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((EditText)getView().findViewById(R.id.edit_url)).getText().toString();
                Log.d(TAG, "url is " + url);
                mWebView.loadUrl(url);
            }
        });
        return v;
    }

    public final class JSInterface {
        //JavaScript脚本代码可以调用的函数onClick()处理
        private View mWebView;
        public JSInterface(View v) {
            mWebView = v;
        }

        @JavascriptInterface
        public void onSizeChanged(final String height) {
            Log.d(TAG, "height : " + height);
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = mWebView.getLayoutParams();
                    lp.height = Integer.parseInt(height) ;
                    mWebView.setLayoutParams(lp);
                }
            });
        }

        public void onClick() {
            mWebView.post(new Runnable() {
                public void run() {

                }
            });
        }
    }
}
