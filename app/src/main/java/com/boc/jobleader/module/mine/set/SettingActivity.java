package com.boc.jobleader.module.mine.set;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.help.CacheDataManager;
import com.boc.jobleader.http.glide.GlideApp;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.LoginApi;
import com.boc.jobleader.http.request.LogoutApi;
import com.boc.jobleader.http.response.LoginBean;
import com.boc.jobleader.http.response.LogoutBean;
import com.boc.jobleader.module.login.LoginActivity;
import com.boc.jobleader.module.mine.bind.BindActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.jobsearchView)
    SettingBar personalBar;

    @BindView(R.id.account)
    SettingBar accountBar;

    @BindView(R.id.clearContent)
    SettingBar clearContent;

    @BindView(R.id.logoutButton)
    Button logoutButton;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();
        // 获取应用缓存大小
        clearContent.setRightText(CacheDataManager.getTotalCacheSize(this));
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
    }

    @OnClick({R.id.jobsearchView, R.id.account, R.id.logoutButton,R.id.clearContent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jobsearchView:
                startActivity(new Intent(this, PersonalActivity.class));
                break;
            case R.id.account:
                startActivity(new Intent(this, BindActivity.class));
                break;
            case R.id.clearContent:
                // 清除内存缓存（必须在主线程）
                GlideApp.get(this).clearMemory();
                new Thread(() -> {
                    CacheDataManager.clearAllCache(this);
                    // 清除本地缓存（必须在子线程）
                    GlideApp.get(this).clearDiskCache();
                    post(() -> {
                        // 重新获取应用缓存大小
                        clearContent.setRightText(CacheDataManager.getTotalCacheSize(this));
                    });
                }).start();
                break;
            case R.id.logoutButton:
                EasyHttp.post(this)
                        .api(new LogoutApi())
                        .request(new HttpCallback<HttpData<LogoutBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<LogoutBean> data) {
                                EasyConfig.getInstance().setParams(new HashMap<>());
                                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("token","");
                                editor.putString("userName","");
                                editor.putString("phone","");
                                editor.putString("nickname","");
                                editor.putString("avator","");
                                editor.commit();

                                MyApplication application = ActivityStackManager.getInstance().getApplication();
                                application.changeRootServer(application);

                                Intent intent = new Intent(application, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                application.startActivity(intent);
                                // 销毁除了登录之外的界面
                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                            }
                        });
                break;
        }
    }
}