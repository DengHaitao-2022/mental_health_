package com.example.pyhy.Login.TreeHole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pyhy.R;

import java.util.List;

public class TreeHoleAdapter extends RecyclerView.Adapter<TreeHoleAdapter.TreeHoleViewHolder> {
    private Context context;
    private List<TreeHole> treeHoleList;

    public TreeHoleAdapter(Context context, List<TreeHole> treeHoleList) {
        this.context = context;
        this.treeHoleList = treeHoleList;
    }

    @Override
    public TreeHoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载 item_tree_hole 布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_tree_hole, parent, false);
        return new TreeHoleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TreeHoleViewHolder holder, int position) {
        TreeHole treeHole = treeHoleList.get(position);
        holder.treeHoleContent.setText(treeHole.getPostContent());
        holder.treeHoleTime.setText(treeHole.getPostTime());
    }

    @Override
    public int getItemCount() {
        return treeHoleList.size();
    }

    public static class TreeHoleViewHolder extends RecyclerView.ViewHolder {
        TextView treeHoleContent, treeHoleTime;

        public TreeHoleViewHolder(View itemView) {
            super(itemView);
            treeHoleContent = itemView.findViewById(R.id.treeHoleContent);
            treeHoleTime = itemView.findViewById(R.id.treeHoleTime);
        }
    }
}
