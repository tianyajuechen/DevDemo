package com.tzy.demo.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.tzy.demo.R;
import com.tzy.demo.bean.WeChatNewsBean;

import java.util.List;

/**
 * Created by YANG
 * 2016/8/3 14:30
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private List<WeChatNewsBean> mNews;

    public NewsAdapter(Context mContext, List<WeChatNewsBean> mNews) {
        this.mContext = mContext;
        this.mNews = mNews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Glide.with(mContext).load(mNews.get(i).getFirstImg()).into(holder.mIv);
        holder.mTvDate.setText(mNews.get(i).getId());
        holder.mTvTitle.setText(mNews.get(i).getTitle());
        holder.mTvDesc.setText(mNews.get(i).getSource());
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv;
        TextView mTvDate;
        TextView mTvTitle;
        TextView mTvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDesc = itemView.findViewById(R.id.tv_desc);

        }
    }
}
