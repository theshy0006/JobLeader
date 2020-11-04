package com.boc.jobleader.module.home.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.dialog.UpdateDialog;
import com.boc.jobleader.module.browser.BrowserActivity;
import com.boc.jobleader.module.workspace.root.WorkSpaceAdapter;
import com.boc.jobleader.module.workspace.root.WorkSpaceHomeItem;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @Nullable
    @BindView(R.id.backButton)
    ImageView backButton;

    @Nullable
    @BindView(R.id.deleteButton)
    ImageView deleteButton;


    @Nullable
    @BindView(R.id.demo)
    RecyclerView demoRecyclerView;

    @Nullable
    @BindView(R.id.history)
    RecyclerView historyRecyclerView;

    private List<String> demoList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();

    private HistoryAdapter adapter2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        super.initData();

        demoList.add("安卓");
        demoList.add("iOS");
        demoList.add("佳能源");
        demoList.add("大数据");

        historyList.add("运营");
        historyList.add("项目管理");
        historyList.add("市场营销");
        historyList.add("项目经理");
        historyList.add("安阳科技");
        historyList.add("阿里巴巴");

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        demoRecyclerView.setLayoutManager(layoutManager);
        DemoAdapter adapter = new DemoAdapter(demoList);
        demoRecyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 4);
        historyRecyclerView.setLayoutManager(layoutManager2);
        adapter2 = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(adapter2);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @OnClick({R.id.backButton, R.id.deleteButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.deleteButton:
                historyList.clear();
                adapter2.notifyDataSetChanged();
                break;
        }
    }

}