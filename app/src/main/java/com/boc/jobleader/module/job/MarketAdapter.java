package com.boc.jobleader.module.job;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boc.jobleader.R;
import com.boc.jobleader.base.MyAdapter;

public final class MarketAdapter extends MyAdapter<String> {

    public MarketAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public com.boc.jobleader.module.job.MarketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.boc.jobleader.module.job.MarketAdapter.ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private TextView mTextView;

        private ViewHolder() {
            super(R.layout.market_item);
        }

        @Override
        public void onBindView(int position) {
        }
    }
}