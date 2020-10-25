package com.boc.jobleader.module.mine.audit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.base.BaseDialog;
import com.boc.jobleader.dialog.MessageDialog;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.AddUserApi;
import com.boc.jobleader.http.request.ChangeMobileApi;
import com.boc.jobleader.http.response.AddUserBean;
import com.boc.jobleader.http.response.UpdateBean;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.authstatus.AuthStatusActivity;
import com.boc.jobleader.module.mine.changemobile.ChangeMobileActivity;
import com.boc.jobleader.module.mine.company.CompanyAuthActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class AuditActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.nameInput)
    EditText nameInput;
    @Nullable
    @BindView(R.id.licenseInput)
    EditText licenseInput;
    @Nullable
    @BindView(R.id.jobInput)
    EditText job;
    @Nullable
    @BindView(R.id.button2)
    Button commitButton;

    private String companyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audit;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();
        companyId = getIntent().getStringExtra("companyId");
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                startActivity(new Intent(AuditActivity.this, AuthenticationActivity.class));
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @OnClick({R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2:
                if (nameInput.getText().toString().length() == 0) {
                    toast("请输入名字");
                    return;
                }
                if (licenseInput.getText().toString().length() == 0) {
                    toast("请输入部门");
                    return;
                }
                if (job.getText().toString().length() == 0) {
                    toast("请输入职位");
                    return;
                }
                showDialog();
                //
                EasyHttp.post(this)
                        .api(new AddUserApi()
                                .setType("员工")
                                .setName(nameInput.getText().toString())
                                .setCompanyId(companyId)
                                .setDepartment(licenseInput.getText().toString()))
                        .request(new HttpCallback<HttpData<AddUserBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<AddUserBean> data) {
                                if (data.getCode() == 500) {
                                    //失败
                                    Intent intent = new Intent(AuditActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 5);
                                    startActivity(intent);
                                } else if (data.getMessage().contains("审核中")) {
                                    Intent intent = new Intent(AuditActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 4);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(AuditActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 3);
                                    startActivity(intent);
                                }
                            }
                        });

                break;


        }
    }
}