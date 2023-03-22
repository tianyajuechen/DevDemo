package com.tzy.demo.activity.packageinfo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityPackageInfoBinding

class PackageInfoActivity : BaseActivity<ActivityPackageInfoBinding>() {

    private val viewModel by viewModels<PackageInfoViewModel>()
    private val adapter by lazy { PackageInfoAdapter() }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onPermissionResult(it)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_package_info
    }

    override fun initView() {
        mBinding.rv.adapter = adapter
        getList()
    }

    override fun initEvent() {
        mBinding.btRefresh.setOnClickListener {
            getList()
        }

        mBinding.btPermission.setOnClickListener {
            requestPermission.launch(Manifest.permission.QUERY_ALL_PACKAGES)
        }

        viewModel.packageList.observe(this) {
            adapter.setList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        setPermissionBtnVisible()
    }

    private fun setPermissionBtnVisible() {
        if (hasPermission()) {
            mBinding.btPermission.visibility = View.GONE
        } else {
            mBinding.btPermission.visibility = View.VISIBLE
        }
    }

    private fun hasPermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.R
            || ContextCompat.checkSelfPermission(this, Manifest.permission.QUERY_ALL_PACKAGES) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 权限结果
     */
    private fun onPermissionResult(grant: Boolean) {
        Toast.makeText(applicationContext, grant.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getList() {
        when(mBinding.rg.checkedRadioButtonId) {
            mBinding.rb1.id -> viewModel.getList1(packageManager)
            mBinding.rb2.id -> viewModel.getList2(packageManager)
            mBinding.rb3.id -> viewModel.getList3(packageManager)
            mBinding.rb4.id -> viewModel.getList4(packageManager, arrayOf(
                "com.android.browser",
                "com.android.contacts",
                "com.android.mms",
                "com.android.settings",
                "com.cmcm.cfwallpaper",
                "com.fq.wallpaper",
                "com.tencent.mm",
                "com.tencent.mobileqq",
                ))
        }
    }
}