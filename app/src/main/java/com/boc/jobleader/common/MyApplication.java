package com.boc.jobleader.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.RequestHandler;
import com.boc.jobleader.http.other.AppConfig;
import com.boc.jobleader.http.server.AuthServer;
import com.boc.jobleader.http.server.ReleaseServer;
import com.boc.jobleader.http.server.TestServer;
import com.boc.jobleader.http.server.UpdateServer;
import com.boc.jobleader.http.server.UserServer;
import com.hjq.bar.TitleBar;
import com.hjq.bar.initializer.LightBarInitializer;
import com.hjq.http.EasyConfig;
import com.hjq.http.config.IRequestServer;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;

import androidx.lifecycle.Lifecycle;

import okhttp3.OkHttpClient;

public class MyApplication extends Application implements LifecycleOwner {

    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        initSdk(this);
        // 初始化 Toast
        ToastUtils.init(this);

        // 初始化 TitleBar
        TitleBar.setDefaultInitializer(new LightBarInitializer());
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSdk(MyApplication application) {
        // 吐司工具类
        ToastUtils.init(application);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });

        // 友盟统计、登录、分享 SDK
        //UmengClient.init(application);


        // Activity 栈管理初始化
        ActivityStackManager.getInstance().init(application);

        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new ReleaseServer();
        }

        EasyConfig.with(new OkHttpClient())
                // 是否打印日志
                .setLogEnabled(AppConfig.isDebug())
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 启用配置
                .into();
    }

    public void changeRootServer(Application application) {
        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new ReleaseServer();
        }

        EasyConfig.with(new OkHttpClient())
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 启用配置
                .into();
    }

    public void changeUserServer(Application application) {
        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new UserServer();
        }

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String appAccessToken =  settings.getString("appAccessToken", "").toString();
        String token =  settings.getString("token", "").toString();
        EasyConfig.with(new OkHttpClient())
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 添加全局请求参数
                .addParam("appAccessToken", appAccessToken)
                .addParam("token", token)
                .addHeader("token", token)
                // 启用配置
                .into();
    }

    public void changeAuthServer(Application application) {
        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new AuthServer();
        }

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String appAccessToken =  settings.getString("appAccessToken", "").toString();
        String token =  settings.getString("token", "").toString();


        EasyConfig.with(new OkHttpClient())
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 添加全局请求参数
                .addParam("appAccessToken", appAccessToken)
                .addParam("token", token)
                .addHeader("token", token)
                // 启用配置
                .into();
    }

    public void changeUpdateServer(Application application) {
        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new UpdateServer();
        }

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String appAccessToken =  settings.getString("appAccessToken", "").toString();
        String token =  settings.getString("token", "").toString();


        EasyConfig.with(new OkHttpClient())
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 添加全局请求参数
                .addParam("appAccessToken", appAccessToken)
                .addParam("token", token)
                .addHeader("token", token)
                // 启用配置
                .into();
    }


}

