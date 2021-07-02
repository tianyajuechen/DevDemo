package com.tzy.demo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tzy.demo.R
import com.tzy.demo.bean.MainItemBean

/**
 * @Author tangzhaoyang
 * @Date 2021/6/30
 *
 */
class MainAdapter : BaseQuickAdapter<MainItemBean, BaseViewHolder>(R.layout.layout_main_item) {

    override fun convert(holder: BaseViewHolder, item: MainItemBean) {
        holder.setText(R.id.tv_name, item.name)
    }

}