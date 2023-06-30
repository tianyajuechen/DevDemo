package com.tzy.demo.activity.paging

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.tzy.demo.bean.NewsBean
import com.tzy.demo.bean.WeChatNewsBean

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
object NewsComparator : DiffUtil.ItemCallback<WeChatNewsBean>() {
    override fun areItemsTheSame(oldItem: WeChatNewsBean, newItem: WeChatNewsBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeChatNewsBean, newItem: WeChatNewsBean): Boolean {
        return oldItem == newItem
    }
}