package com.tzy.demo.activity.packageinfo

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.activity.main.MainActivity
import com.tzy.demo.bean.PackageInfoBean
import com.tzy.demo.databinding.ActivityPackageInfoBinding

class PackageInfoActivity : BaseActivity<ActivityPackageInfoBinding>() {

    private val TAG = "PackageInfoActivity"
    private val viewModel by viewModels<PackageInfoViewModel>()
    private val adapter by lazy { PackageInfoAdapter() }
    private lateinit var receiver: ShortcutReceiver
    private var shortcutList: MutableList<PackageInfoBean>? = null

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onPermissionResult(it)
    }

    companion object {
        const val PACKAGE_NAME = "package_name"
        const val ACTION_INSTALL_SHORTCUT = "com.tzy.demo.ACTION_INSTALL_SHORTCUT"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_package_info
    }

    override fun initView() {
        mBinding.rv.adapter = adapter
        getList()

        registerReceiver()
    }

    override fun initEvent() {
        mBinding.btRefresh.setOnClickListener {
            getList()
        }

        mBinding.btPermission.setOnClickListener {
            requestPermission.launch(Manifest.permission.QUERY_ALL_PACKAGES)
        }

        mBinding.btShortcut.setOnClickListener {
            generateAllShortcut()
        }

        adapter.setOnItemClickListener { _, _, position ->
            val info = adapter.getItem(position)
            itemClick(info)
        }

        viewModel.packageList.observe(this) {
            adapter.setList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        setPermissionBtnVisible()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver()
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

    private fun generateAllShortcut() {
        if (adapter.data.isEmpty()) return
        shortcutList = adapter.data.toMutableList()
        generateShortcutCompat(shortcutList!![0])
    }

    private fun traverseGenerateShortcut() {
        if (shortcutList.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "添加结束", Toast.LENGTH_SHORT).show()
            return
        }
        generateShortcutCompat(shortcutList!![0])
        shortcutList?.removeAt(0)
    }

    private fun itemClick(item: PackageInfoBean) {
        generateShortcutCompat(item)
    }

    private fun generateShortcut(item: PackageInfoBean) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        if (!shortcutManager.isRequestPinShortcutSupported) return
        val shortcutInfo = ShortcutInfo.Builder(applicationContext, item.packageName)
            .setIcon(Icon.createWithBitmap(item.icon.toBitmap()))
            .setShortLabel(item.name)
            .setLongLabel(item.name)
            .setIntent(getAppLauncherIntent(item.packageName))
            .build()
        val callbackIntent = shortcutManager.createShortcutResultIntent(shortcutInfo)
        val successCallback = PendingIntent.getBroadcast(applicationContext, 0, callbackIntent, 0)

        shortcutManager.requestPinShortcut(shortcutInfo, successCallback.intentSender)
    }

    private fun generateShortcutCompat(item: PackageInfoBean) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        if (!ShortcutManagerCompat.isRequestPinShortcutSupported(applicationContext)) return
        val shortcutInfo = ShortcutInfoCompat.Builder(applicationContext, item.packageName)
            .setIcon(IconCompat.createWithBitmap(item.icon.toBitmap()))
            .setShortLabel(item.name)
            .setLongLabel(item.name)
            .setIntent(getAppLauncherIntent(item.packageName))
            .build()
        val callbackIntent = Intent(Intent.ACTION_CREATE_SHORTCUT).apply {
            putExtra(PACKAGE_NAME, item.packageName)
        }
        val successCallback = PendingIntent.getBroadcast(applicationContext, 200, callbackIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        ShortcutManagerCompat.requestPinShortcut(applicationContext, shortcutInfo, successCallback.intentSender)
    }

    private fun getAppLauncherIntent(packageName: String): Intent {
        var intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setPackage(packageName)
        return intent
    }

    private fun registerReceiver() {
        receiver = ShortcutReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_CREATE_SHORTCUT)
        registerReceiver(receiver, filter)
    }

    private fun unregisterReceiver() {
        unregisterReceiver(receiver)
    }

    inner class ShortcutReceiver : BroadcastReceiver() {
        private val TAG = "ShortcutReceiver"
        override fun onReceive(context: Context, intent: Intent) {
            Log.e(TAG, intent.action + " " + intent.getStringExtra(PACKAGE_NAME))
            if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
                traverseGenerateShortcut()
            }
        }

    }
}