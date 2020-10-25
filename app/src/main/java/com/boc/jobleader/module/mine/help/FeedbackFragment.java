package com.boc.jobleader.module.mine.help;

import android.content.SharedPreferences;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class FeedbackFragment extends BaseFragment {

    @NotNull
    @BindView(R.id.webView1)
    WebView webView;

    private  WebSettings webSettings;

    private void webShow(){
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());

        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        String appAccessToken =  settings.getString("appAccessToken", "").toString();
        String token =  settings.getString("token", "").toString();

        String fs;
        fs = String.format("http://localhost:8080/#/pages/feedback/feedback?appAccessToken=%s&token=%s",
                appAccessToken, token);
        webView.loadUrl(fs);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initView() {
        super.initView();

        webShow();
    }

}