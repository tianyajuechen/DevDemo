package com.tzy.demo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tzy.demo.R;
import com.tzy.demo.bean.NewsBean;

import java.util.List;

/**
 * Created by YANG
 * 2016/8/4 15:23
 */

public class QuickAdapter extends BaseQuickAdapter<NewsBean> {

    private Context mContext;

    public QuickAdapter(List<NewsBean> data, Context context) {
        super(R.layout.news_item_layout, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final NewsBean newsBean) {
        holder.setText(R.id.tv_date, newsBean.getCtime())
                .setText(R.id.tv_desc, newsBean.getDescription())
                .setText(R.id.tv_title, newsBean.getTitle());
        Glide.with(mContext).load(newsBean.getPicUrl()).crossFade().into((ImageView) holder.getView(R.id.iv));

        holder.setOnClickListener(R.id.tv_title, new OnItemChildClickListener())
                .setOnClickListener(R.id.tv_date, new OnItemChildClickListener())
                .setOnClickListener(R.id.tv_desc, new OnItemChildClickListener());
        /*holder.setOnClickListener(R.id.tv_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, newsBean.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(R.id.tv_date, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, newsBean.getCtime(), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(R.id.tv_desc, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, newsBean.getDescription(), Snackbar.LENGTH_SHORT).show();
                    }
                });*/
    }
}
