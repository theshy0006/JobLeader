package com.boc.jobleader.module.mine.aboutme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.dialog.UpdateDialog;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.CheckUpdateApi;
import com.boc.jobleader.http.request.GetAppAccessTokenApi;
import com.boc.jobleader.http.response.AppAccessToken;
import com.boc.jobleader.http.response.CheckUpdateBean;
import com.boc.jobleader.module.browser.BrowserActivity;
import com.boc.jobleader.module.mine.company.CompanyAuthActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AboutmeActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.currentTextView)
    TextView currentTextView;
    @Nullable
    @BindView(R.id.versionUpdate)
    SettingBar versionUpdate;
    @Nullable
    @BindView(R.id.userPravicy)
    SettingBar userPravicy;
    @Nullable
    @BindView(R.id.userAgree)
    SettingBar userAgree;

    private int versionCode;
    private String versonName;
    private int futureCode;
    private String futureName;
    private String content;
    private String url;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutme;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        super.initData();

        try {
            versonName = getPackageManager().getPackageInfo("com.boc.jobleader", 0).versionName;
            versionCode = getPackageManager().getPackageInfo("com.boc.jobleader", 0).versionCode;
            versionUpdate.setRightText("最新版本 " + versonName);

            currentTextView.setText("Version " + versonName);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }

    @Override
    protected void initView() {
        super.initView();

        // 连接root服务器获取appAccessToken
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeRootServer(application);

        // 刷新用户信息
        EasyHttp.post(this)
                .api(new CheckUpdateApi()
                .setVersionCode("50")
                .setSystem(0))
                .request(new HttpCallback<HttpData<CheckUpdateBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<CheckUpdateBean> data) {

                        if (data.getMessage().contains("无需更新")){
                            futureName = versonName;
                            futureCode = versionCode;
                        } else {
                            futureName = data.getData().getVersionName().toString();
                            futureCode = data.getData().getVersionCode();
                            content = data.getData().getContent().toString();
                            url = data.getData().getUrl().toString();
                        }



                        versionUpdate.setRightText("最新版本 " + data.getData().getVersionName().toString());
                    }
                });





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

    @OnClick({R.id.userPravicy, R.id.userAgree, R.id.versionUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userPravicy:
                BrowserActivity.start(this, "https://www.buick.com.cn/privacy.html");
                break;
            case R.id.userAgree:
                BrowserActivity.start(this, "https://in.m.jd.com/help/app/register_info.html");
                break;

            case R.id.versionUpdate:

                if(futureCode >= versionCode) {
                    // 升级对话框
                    new UpdateDialog.Builder(this)
                            // 版本名
                            .setVersionName(futureName)
                            // 是否强制更新
                            .setForceUpdate(false)
                            // 更新日志
                            .setUpdateLog(content)
                            // 下载 URL
                            .setDownloadUrl(url)
                            // 文件 MD5
                            .setFileMd5("6ec99cb762ffd9158e8b27dc33d9680d")
                            .show();
                } else {
                    toast("您当前已经是最新版本");
                }


                break;


        }
    }

    @Override
    public void finish() {
        super.finish();

        // 连接root服务器获取appAccessToken
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);


    }
}