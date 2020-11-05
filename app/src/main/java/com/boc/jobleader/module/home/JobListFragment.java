package com.boc.jobleader.module.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;
import com.boc.jobleader.action.HandlerAction;
import com.boc.jobleader.base.BaseAdapter;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.custom.WrapRecyclerView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JobListFragment extends BaseFragment implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, HandlerAction {

    public static com.boc.jobleader.module.home.JobListFragment newInstance() {
        return new com.boc.jobleader.module.home.JobListFragment();
    }

    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joblist;
    }

    private HomeAdapter mAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色

        mAdapter = new HomeAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setData(analogData());
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        ToastUtils.show(mAdapter.getItem(position));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.addData(analogData());
            mRefreshLayout.finishLoadMore();
        }, 1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.clearData();
            mAdapter.setData(analogData());
            mRefreshLayout.finishRefresh();
        }, 1000);
    }
}