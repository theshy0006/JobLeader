package com.boc.jobleader.module.mine.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.base.BaseDialog;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.dialog.MessageDialog;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.model.HttpListData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CompanyAddApi;
import com.boc.jobleader.http.request.FindCompanyApi;
import com.boc.jobleader.http.request.GetMyAuthCompanyApi;
import com.boc.jobleader.http.request.UpdateImageApi;
import com.boc.jobleader.http.request.UpdatePasswordApi;
import com.boc.jobleader.http.response.CompanyAddBean;
import com.boc.jobleader.http.response.CompanyAuthList;
import com.boc.jobleader.http.response.FindCompanyBean;
import com.boc.jobleader.http.response.UpdateImageBean;
import com.boc.jobleader.http.response.UpdatePasswordBean;
import com.boc.jobleader.module.image.ImageSelectActivity;
import com.boc.jobleader.module.mine.audit.AuditActivity;
import com.boc.jobleader.module.mine.authstatus.AuthStatusActivity;
import com.boc.jobleader.module.mine.changemobile.ChangeMobileActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.io.File;
import java.util.List;

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

    private String yingyeImageUrl = "";

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

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符即将被长度为after的新文本所取代。
                // 在这个方法里面改变s，会报错。
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符刚刚取代了长度为before的旧文本。
                // 在这个方法里面改变s，会报错。
            }
            @Override
            public void afterTextChanged(Editable s) {
                //这个方法被调用，那么说明s字符串的某个地方已经被改变。

                FindCompany(s.toString());
            }
        });

    }

    public void FindCompany(String name) {
        if(name.length() == 0) {
            return;
        }
        // 查询认证信息
        EasyHttp.post(this)
                .api(new FindCompanyApi()
                .setFullName(nameInput.getText().toString()))
                .request(new HttpCallback<HttpData<FindCompanyBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<FindCompanyBean> data) {

                        Integer exit = data.getData().getExit();

                        if (exit != 0) {
                            // 企业还没有认证过
                        } else {
                            // 企业已经认证过

                            new MessageDialog.Builder(CompanyAuthActivity.this)
                                    // 标题可以不用填写
                                    .setTitle("公司已认证")
                                    // 内容必须要填写
                                    .setMessage("请填写个人信息审核")
                                    // 确定按钮文本
                                    .setConfirm("去审核")
                                    // 设置 null 表示不显示取消按钮
                                    .setCancel(getString(R.string.common_cancel))
                                    // 设置点击按钮后不关闭对话框
                                    //.setAutoDismiss(false)
                                    .setListener(new MessageDialog.OnListener() {

                                        @Override
                                        public void onConfirm(BaseDialog dialog) {
                                            startActivity(new Intent(CompanyAuthActivity.this, AuditActivity.class));
                                        }

                                        @Override
                                        public void onCancel(BaseDialog dialog) {

                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }



    @OnClick({R.id.zhengjianImageView, R.id.updateButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhengjianImageView:


                ImageSelectActivity.start(this, data -> {

                    if (true) {
                        yingyeImageUrl = data.get(0);
                        Glide.with(this)
                                .load(yingyeImageUrl)
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(zhengjianImageView);
                    }

                    MyApplication application = ActivityStackManager.getInstance().getApplication();
                    application.changeUpdateServer(application);
                    // 上传头像
                    EasyHttp.post(this)
                            .api(new UpdateImageApi()
                                    .setFile(new File(yingyeImageUrl)))
                            .request(new HttpCallback<HttpData<UpdateImageBean>>(CompanyAuthActivity.this) {

                                @Override
                                public void onSucceed(HttpData<UpdateImageBean> data) {
                                    yingyeImageUrl = data.getData().getUrl().toString();
                                    Glide.with(CompanyAuthActivity.this)
                                            .load(yingyeImageUrl)
                                            .into(zhengjianImageView);
                                }
                            });
                });
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

                MyApplication application = ActivityStackManager.getInstance().getApplication();
                application.changeAuthServer(application);
                // 企业认证
                EasyHttp.post(this)
                        .api(new CompanyAddApi()
                                .setFullName(nameInput.getText().toString())
                                .setBusinessLicence(yingyeImageUrl)
                                .setDutyParagraphNumber(licenseInput.getText().toString()))
                        .request(new HttpCallback<HttpData<CompanyAddBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<CompanyAddBean> data) {
                                super.onSucceed(data);
                                startActivity(new Intent(CompanyAuthActivity.this, AuthStatusActivity.class));

                            }
                        });

                break;
        }
    }
}