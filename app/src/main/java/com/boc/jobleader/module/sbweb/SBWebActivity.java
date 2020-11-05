package com.boc.jobleader.module.sbweb;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.boc.jobleader.base.BaseActivity;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebSettings;

import com.boc.jobleader.R;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class SBWebActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.webview2)
    BridgeWebView webView;

    private String title;
    private String url;

    private WebSettings settings;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_s_b_web;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
        }


        mTitleBar.setTitle(title);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
            }
        });

        initWebView();
    }

    private void initWebView() {
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 设置与Js交互的权限
        settings.setJavaScriptCanOpenWindowsAutomatically(true);// 设置允许JS弹窗
        //设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setGeolocationEnabled(true);
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        settings.setDefaultTextEncodingName("UTF-8");

        // 屏幕自适应
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);

        webView.registerHandler("saveCity", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                System.out.println(data);
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("currentCity", data);
                editor.commit();
                finish();
            }
        });

        webView.registerHandler("saveService", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                System.out.println(data);
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();

                String[] service = data.split("\\+");

                if(service.length == 3) {
                    editor.putString("serviceOne", service[0]);
                    editor.putString("serviceTwo", service[1]);
                    editor.putString("serviceThree", service[2]);
                    editor.commit();
                }
                finish();
            }
        });

        webView.registerHandler("saveItem", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                System.out.println(data);
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();

                String[] item = data.split("\\+");

                if(item.length == 2) {
                    editor.putString("itemOne", item[0]);
                    editor.putString("itemTwo", item[1]);
                    editor.commit();
                } else if(item.length == 3) {
                    editor.putString("itemOne", item[0]);
                    editor.putString("itemTwo", item[1]);
                    editor.putString("itemThree", item[2]);
                    editor.commit();
                } else if(item.length == 4) {
                    editor.putString("itemOne", item[0]);
                    editor.putString("itemTwo", item[1]);
                    editor.putString("itemThree", item[2]);
                    editor.putString("itemFour", item[3]);
                    editor.commit();
                } else {
                    editor.putString("itemOne", item[0]);
                    editor.putString("itemTwo", item[1]);
                    editor.putString("itemThree", item[2]);
                    editor.putString("itemFour", item[3]);
                    editor.putString("itemFive", item[4]);
                    editor.commit();
                }

                finish();
            }
        });

        webView.registerHandler("saveworkspace", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                System.out.println(data);
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("saveworkspace", data);
                editor.commit();
                finish();
            }
        });


        webView.loadUrl(url);
    }

}
