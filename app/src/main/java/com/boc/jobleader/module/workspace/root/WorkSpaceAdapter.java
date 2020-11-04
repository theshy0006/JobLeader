package com.boc.jobleader.module.workspace.root;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boc.jobleader.R;

import java.util.ArrayList;
import java.util.List;

public class WorkSpaceAdapter extends RecyclerView.Adapter<WorkSpaceAdapter.ViewHolder> {
    private List<WorkSpaceHomeItem> datasource;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.item_url);
            fruitName = (TextView) view.findViewById(R.id.item_name);
        }
    }
    public WorkSpaceAdapter(List<WorkSpaceHomeItem> fruitList) {
        datasource = fruitList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workspace_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkSpaceHomeItem fruit = datasource.get(position);
        holder.fruitImage.setImageResource(fruit.getItemSrouce());
        holder.fruitName.setText(fruit.getItemName());
    }
    @Override
    public int getItemCount() {
        return datasource.size();
    }
}
