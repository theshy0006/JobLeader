package com.boc.jobleader.module.home.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<com.boc.jobleader.module.home.search.HistoryAdapter.ViewHolder> {
    private List<String> datasource;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitName = (TextView) view.findViewById(R.id.item_name);
        }
    }
    public HistoryAdapter(List<String> fruitList) {
        datasource = fruitList;
    }
    @Override
    public com.boc.jobleader.module.home.search.HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        com.boc.jobleader.module.home.search.HistoryAdapter.ViewHolder holder = new com.boc.jobleader.module.home.search.HistoryAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(com.boc.jobleader.module.home.search.HistoryAdapter.ViewHolder holder, int position) {
        String fruit = datasource.get(position);
        holder.fruitName.setText(fruit);
    }
    @Override
    public int getItemCount() {
        return datasource.size();
    }
}