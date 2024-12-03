package com.example.pyhy.playPng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pyhy.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter<T> extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<T> imageList;  // 泛型列表，可以是 String 或 Integer
    private Context context;
    private boolean isFromUrlList;  // 标志位，用于判断当前使用的是 URL 列表还是资源 ID 列表

    // 构造函数：接受 URL 列表
    public ViewPagerAdapter(Context context, List<T> imageList, boolean isFromUrlList) {
        this.context = context;
        this.imageList = (imageList != null) ? imageList : new ArrayList<T>(); // 防止传入 null
        this.isFromUrlList = isFromUrlList;  // 根据参数决定是否是 URL 列表
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isFromUrlList) {
            // 如果是 URL 列表，且 imageList 中是 String 类型
            String imageUrl = (String) imageList.get(position);  // 强制类型转换
            Glide.with(context)
                    .load(imageUrl) // 加载 URL 图片
                    .into(holder.imageView);
        } else {
            // 如果是资源 ID 列表，且 imageList 中是 Integer 类型
            int imageResource = (int) imageList.get(position);  // 强制类型转换
            Glide.with(context)
                    .load(imageResource) // 加载资源 ID 图片
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image); // 绑定 ImageView
        }
    }
}
