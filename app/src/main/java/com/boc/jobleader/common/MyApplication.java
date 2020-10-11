package com.boc.jobleader.common;

import android.app.Application;

import com.hjq.bar.TitleBar;
import com.hjq.bar.initializer.LightBarInitializer;
import com.hjq.toast.ToastUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 Toast
        ToastUtils.init(this);

        // 初始化 TitleBar
        TitleBar.setDefaultInitializer(new LightBarInitializer());
    }
}

