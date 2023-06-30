package com.tzy.demo.activity.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tzy.demo.R
import com.tzy.demo.bean.WeChatNewsBean

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
class NewsAdapter(diffCallback: DiffUtil.ItemCallback<WeChatNewsBean>) : PagingDataAdapter<WeChatNewsBean, BaseViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position) ?: return
        Glide.with(holder.itemView.context).load(item.firstImg).into(holder.getView(R.id.iv))
        holder.setText(R.id.tv_date, item.id)
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_desc, item.source)
    }
}