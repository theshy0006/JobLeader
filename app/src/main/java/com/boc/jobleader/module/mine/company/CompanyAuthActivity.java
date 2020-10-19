package com.boc.jobleader.module.mine.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CompanyAddApi;
import com.boc.jobleader.http.request.UpdatePasswordApi;
import com.boc.jobleader.http.response.CompanyAddBean;
import com.boc.jobleader.http.response.UpdatePasswordBean;
import com.boc.jobleader.module.mine.authstatus.AuthStatusActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyAuthActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.nameInput)
    EditText nameInput;

    @BindView(R.id.licenseInput)
    EditText licenseInput;

    @BindView(R.id.updateButton)
    Button updateButton;

    private String yingyeImageUrl = "https://img.yzcdn.cn/vant/cat.jpeg";

    @BindView(R.id.zhengjianImageView)
    ImageView zhengjianImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_auth;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();

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

    @OnClick({R.id.zhengjianImageView, R.id.updateButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhengjianImageView:
                break;

            case R.id.updateButton:
                //密码登录模式
                if (nameInput.getText().toString().length() == 0) {
                    toast("请输入公司名称");
                    return;
                }

                if (licenseInput.getText().toString().length() == 0) {
                    toast("请输入营业执照号");
                    return;
                }

                if (yingyeImageUrl.length() == 0) {
                    toast("请上传营业执照照片");
                    return;
                }


                // 企业认证
                EasyHttp.post(this)
                        .api(new CompanyAddApi()
                                .setCompanyName(nameInput.getText().toString())
                                .setBusinessLicence(licenseInput.getText().toString())
                                .setFaceCardImg(yingyeImageUrl))
                        .request(new HttpCallback<HttpData<CompanyAddBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<CompanyAddBean> data) {
                                toast(data.getMessage().toString());
                                startActivity(new Intent(CompanyAuthActivity.this, AuthStatusActivity.class));

                            }
                        });

                break;
        }
    }
}