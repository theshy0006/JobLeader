package com.boc.jobleader.module.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;
import com.boc.jobleader.action.HandlerAction;
import com.boc.jobleader.base.BaseAdapter;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.custom.StatusAdapter;
import com.boc.jobleader.custom.WrapRecyclerView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyFragment extends BaseFragment implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, HandlerAction {

    public static ApplyFragment newInstance() {
        return new ApplyFragment();
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
        return data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply;
    }

    private StatusAdapter mAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色

        mAdapter = new StatusAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setData(analogData());
        mRecyclerView.setAdapter(mAdapter);

        View footerView = mRecyclerView.addFooterView(R.layout.apply_item);
        footerView.setOnClickListener(v -> ToastUtils.show("点击了尾部"));

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