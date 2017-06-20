package com.tzy.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
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
        @Bind(R.id.iv)
        ImageView mIv;
        @Bind(R.id.tv_date)
        TextView mTvDate;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_desc)
        TextView mTvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
