package com.example.pyhy.Login.DiaryWrite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pyhy.R;
import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private ArrayList<Diary> diaryList;

    // 构造函数，接收一个Diary列表
    public DiaryAdapter(ArrayList<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    // 创建新的ViewHolder，返回一个新的DiaryViewHolder实例
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 使用布局填充器加载 item_diary 布局文件
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new DiaryViewHolder(itemView);
    }

    // 将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        // 获取当前日记对象
        Diary diary = diaryList.get(position);

        // 设置日记文本
        holder.textDiary.setText(diary.getText());

        // 如果有图片，加载图片；否则隐藏 ImageView
        if (diary.getImagePath() != null ) {
            holder.imageDiary.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())  // 使用Glide加载图片
                    .load(diary.getImagePath())        // 图片路径
                    .error(R.drawable.p_2)     // 图片加载失败时显示的默认图片
                    .fallback(R.drawable.p_1)  // 如果图片路径为空或无效时显示的占位符
                    .into(holder.imageDiary);
        } else {
            holder.imageDiary.setVisibility(View.GONE);  // 没有图片时隐藏ImageView
        }

        // 设置时间戳
        holder.textTimestamp.setText(diary.getTimestamp());
    }

    // 返回数据的总个数
    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    // 定义ViewHolder，持有每一项的视图
    public static class DiaryViewHolder extends RecyclerView.ViewHolder {
        public TextView textDiary;
        public ImageView imageDiary;
        public TextView textTimestamp;

        // 构造函数，初始化每个视图元素
        public DiaryViewHolder(View itemView) {
            super(itemView);
            textDiary = itemView.findViewById(R.id.textDiary);
            imageDiary = itemView.findViewById(R.id.imageDiary);
            textTimestamp = itemView.findViewById(R.id.textTimestamp);
        }
    }
}
