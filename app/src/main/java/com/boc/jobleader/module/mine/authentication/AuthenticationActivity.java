package com.boc.jobleader.module.mine.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.model.HttpListData;
import com.boc.jobleader.http.request.GetAppAccessTokenApi;
import com.boc.jobleader.http.request.GetMyAuthCompanyApi;
import com.boc.jobleader.http.response.AppAccessToken;
import com.boc.jobleader.http.response.CompanyAuthList;
import com.boc.jobleader.http.response.CompanyBean;
import com.boc.jobleader.module.login.LoginActivity;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.company.CompanyAuthActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.companyStatus)
    TextView companyStatus;
    @Nullable
    @BindView(R.id.companyView)
    ConstraintLayout companyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeAuthServer(application);

        // 查询认证信息
        EasyHttp.post(this)
                .api(new GetMyAuthCompanyApi())
                .request(new HttpCallback<HttpListData<CompanyAuthList>>(this) {

                    @Override
                    public void onSucceed(HttpListData<CompanyAuthList> data) {
                        List<CompanyAuthList> list = data.getData();
                        if (list.size() == 0) {
                            companyStatus.setText("未认证");
                            companyStatus.setTextColor(0xffcccccc);
                        } else {
                            companyStatus.setText("已认证");
                            companyStatus.setTextColor(0xff666666);
                        }
                    }
                });


    }

    @Override
    protected void initView() {
        super.initView();

        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {

                MyApplication application = ActivityStackManager.getInstance().getApplication();
                application.changeRootServer(application);

                Intent intent = new Intent(application, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(intent);
                // 销毁除了登录之外的界面
                ActivityStackManager.getInstance().finishAllActivities(MainActivity.class);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @OnClick({R.id.companyView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.companyView:

                if ("未认证".equals(companyStatus.getText().toString())) {
                    startActivity(new Intent(this, CompanyAuthActivity.class));
                    break;
                } else {
                    toast("您已经完成认证");
                }


        }
    }
}