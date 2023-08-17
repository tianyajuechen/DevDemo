package com.tzy.demo.activity.callshow

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityCallShowBinding
import com.tzy.demo.utils.FileUtil
import com.tzy.demo.utils.PermissionUtil
import java.io.File

/**
 * @Author tangzhaoyang
 * @Date 2023/8/16
 * 来电秀
 */
class CallShowActivity : BaseActivity<ActivityCallShowBinding>() {

    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        arrayOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else {
        arrayOf(
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        onPermissionResult(it)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_call_show
    }

    override fun initView() {

    }

    override fun initEvent() {
        mBinding.btApplyPermission.setOnClickListener { applyPermission() }

        mBinding.btStartService.setOnClickListener {
            val intent = Intent(this, CallListenService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }

        mBinding.btStopService.setOnClickListener {
            val intent = Intent(this, CallListenService::class.java)
            stopService(intent)
        }

        mBinding.btSetRingtone.setOnClickListener {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES), "yuechan.mp3")
            if (file.exists()) {
                PhoneUtil.setRing(this, file.path)
            } else {
                Thread {
                    FileUtil.assetsToFile(this, "call/yuechan.mp3", file.path)
                    runOnUiThread { PhoneUtil.setRing(this, file.path) }
                }.start()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        updatePermissionInfo()
    }

    private fun updatePermissionInfo() {
        var info: String = "悬浮窗：${PermissionUtil.canDrawOverlays(this)}\n"
            .plus("通知监听：${PermissionUtil.isNotificationListenEnable(this)}\n")
            .plus("WRITE_SETTINGS：${PermissionUtil.canWriteSetting(this)}\n")
        permissions.forEach {
            info += "$it：${PermissionUtil.grantedPermission(this, it)}\n"
        }
        mBinding.tvPermissionStatus.text = info
    }

    private fun applyPermission() {

        requestPermission.launch(permissions)
    }

    private fun onPermissionResult(result: Map<String, Boolean>) {
        if (!PermissionUtil.canDrawOverlays(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
            }
            return
        }
        if (!PermissionUtil.isNotificationListenEnable(this)) {
            PermissionUtil.openNotificationListenSettings(this)
            return
        }
        if (!PermissionUtil.canWriteSetting(this)) {
            PermissionUtil.openWriteSetting(this)
            return
        }
        Toast.makeText(this, "已获取所有权限", Toast.LENGTH_SHORT).show()
    }
}