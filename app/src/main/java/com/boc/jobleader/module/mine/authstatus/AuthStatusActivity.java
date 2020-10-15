package com.boc.jobleader.module.mine.authstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class AuthStatusActivity extends BaseActivity {
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    // 0:success 1:fail 2:unknown
    private int type = 0;

    @BindView(R.id.statusImage)
    ImageView imageView;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    TextView content;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth_status;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }



    @Override
    protected void initView() {
        super.initView();

        if( type == 0 ){
            imageView.setImageResource(R.mipmap.icon_tips_success);
            title.setText("认证成功");
            content.setText("恭喜你，身份认证已通过");
        } else if( type == 1 ){
            imageView.setImageResource(R.mipmap.icon_tips_fail);
            title.setText("审核中");
            content.setText("提交成功，等待审核中");
        } else if( type == 2 ){
            imageView.setImageResource(R.mipmap.icon_tips_review);
            title.setText("认证失败");
            content.setText("认证失败，请重新认证");
        }

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
}