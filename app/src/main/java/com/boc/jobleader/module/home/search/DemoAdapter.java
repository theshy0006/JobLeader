package com.boc.jobleader.module.home.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;
import com.boc.jobleader.module.workspace.root.WorkSpaceHomeItem;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<com.boc.jobleader.module.home.search.DemoAdapter.ViewHolder> {
    private List<String> datasource;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitName = (TextView) view.findViewById(R.id.item_name);
        }
    }
    public DemoAdapter(List<String> fruitList) {
        datasource = fruitList;
    }
    @Override
    public com.boc.jobleader.module.home.search.DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        com.boc.jobleader.module.home.search.DemoAdapter.ViewHolder holder = new com.boc.jobleader.module.home.search.DemoAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(com.boc.jobleader.module.home.search.DemoAdapter.ViewHolder holder, int position) {
        String fruit = datasource.get(position);
        holder.fruitName.setText(fruit);
        if(position ==0) {
            holder.fruitName.setBackgroundResource(R.drawable.bg_btn1);
        } else if(position ==1) {
            holder.fruitName.setBackgroundResource(R.drawable.bg_btn2);
        } else if(position ==2) {
            holder.fruitName.setBackgroundResource(R.drawable.bg_btn3);
        } else {
            holder.fruitName.setBackgroundResource(R.drawable.bg_btn4);
        }

    }
    @Override
    public int getItemCount() {
        return datasource.size();
    }
}