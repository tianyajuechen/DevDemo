package com.tzy.demo.activity.packageinfo

import android.content.pm.PackageInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tzy.demo.R
import com.tzy.demo.application.MyApp
import com.tzy.demo.bean.PackageInfoBean

class PackageInfoAdapter : BaseQuickAdapter<PackageInfoBean, BaseViewHolder>(R.layout.item_app_package_info, null) {

    override fun convert(holder: BaseViewHolder, item: PackageInfoBean) {
        holder.setImageDrawable(R.id.iv_icon, item.icon)
        val name = "${item.name}|${item.packageName}"
        holder.setText(R.id.tv_name, name)
        holder.setText(R.id.tv_info, item.launcher ?: "")
    }
}