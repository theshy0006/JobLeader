package com.boc.jobleader.module.root;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.http.request.GetAppAccessTokenApi;
import com.boc.jobleader.http.response.AppAccessToken;
import com.boc.jobleader.module.login.LoginActivity;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.boc.jobleader.R;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.UserInfoApi;
import com.boc.jobleader.http.response.UserInfoBean;
import com.boc.jobleader.http.other.AppConfig;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;

import static java.lang.Thread.sleep;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 闪屏界面
 */
public final class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                String token =  settings.getString("token", "").toString();
                String appAccessToken =  settings.getString("appAccessToken", "").toString();

                if (token.length() != 0 && appAccessToken.length() != 0) {
                    EasyConfig.getInstance().addParam("token", token);
                    EasyConfig.getInstance().addHeader("token", token);
                    EasyConfig.getInstance().addParam("appAccessToken", appAccessToken);

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);    //延时1s执行
    }

    @Override
    protected void initData() {


    }

    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(false).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }
}