package com.tzy.demo.activity.scheme

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivitySchemeTestBinding

class SchemeTestActivity : BaseActivity<ActivitySchemeTestBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scheme_test
    }

    override fun initView() {

    }

    override fun initEvent() {
        mBinding.btGo.setOnClickListener {
            val scheme = mBinding.etScheme.text.toString().trim()
            if (scheme.isEmpty()) return@setOnClickListener
            val host = mBinding.etHost.text.toString().trim()
            val path = mBinding.etPath.text.toString().trim()
            val text = if (host.isEmpty()) {
                "$scheme://"
            } else {
                if (path.isEmpty()) {
                    "$scheme://$host"
                } else {
                    "$scheme://$host/$path"
                }
            }
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                addCategory(Intent.CATEGORY_DEFAULT)
                addCategory(Intent.CATEGORY_BROWSABLE)
                data = Uri.parse(text)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "请选择"))
            } else {
                Toast.makeText(this, "您的手机不支持此协议", Toast.LENGTH_SHORT).show()
            }
        }
    }
}