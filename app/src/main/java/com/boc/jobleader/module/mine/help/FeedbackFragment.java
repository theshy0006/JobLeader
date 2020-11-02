package com.boc.jobleader.module.mine.help;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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

    private ValueCallback<Uri[]> filePathCallback1;

    private  WebSettings webSettings;

    private void webShow(){
        webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //开启JavaScript支持
        webSettings.setJavaScriptEnabled(true);
        // 支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(false);//这里一定得是false,不然打开的网页中，不能在点击打开了
        webView.setWebViewClient(new MyWebViewClient());


        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                //启动系统相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,99);
                filePathCallback1   = filePathCallback;
                return true;
            }
        });




        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        String appAccessToken =  settings.getString("appAccessToken", "").toString();
        String token =  settings.getString("token", "").toString();

        String fs;
        fs = String.format("http://122.192.73.178:8082/jobleader/#/pages/feedback/feedback?appAccessToken=%s&token=%s",
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //99为之前设置中的标识
        if (requestCode==99){
            if (null!=data){
                Uri selectedImage = data.getData();
                filePathCallback1.onReceiveValue(new Uri[]{selectedImage});
            } else {
                //没有选择图片,返回空,不返回的话,点击过一次以后,无法再启动相册
                filePathCallback1.onReceiveValue(new Uri[]{});
            }
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