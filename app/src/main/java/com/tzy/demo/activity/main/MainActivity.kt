package com.tzy.demo.activity.main

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tzy.demo.R
import com.tzy.demo.activity.animator.AnimatorActivity
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.activity.camera.CameraActivity
import com.tzy.demo.activity.downloadprogress.OkhttpDownloadActivity
import com.tzy.demo.activity.js.TestJsActivity
import com.tzy.demo.activity.largeimage.LargeImageActivity
import com.tzy.demo.activity.material.CoordinatorActivity
import com.tzy.demo.activity.media.MediaPlayerActivity
import com.tzy.demo.activity.mvvm.DataBindingTestActivity
import com.tzy.demo.activity.recyclerview.MultiRecyclerViewActivity
import com.tzy.demo.activity.scheme.SchemeTestActivity
import com.tzy.demo.activity.serialize.SerializeTestActivity
import com.tzy.demo.activity.sms.ReadSmsActivity
import com.tzy.demo.activity.stackview.StackViewActivity
import com.tzy.demo.activity.tablayout.TabLayoutActivity
import com.tzy.demo.activity.task.TaskA1Activity
import com.tzy.demo.activity.wallpaper.WallpaperActivity
import com.tzy.demo.activity.web.CommonWebviewActivity
import com.tzy.demo.activity.websocket.WebSocketActivity
import com.tzy.demo.activity.wifi.WiFiPasswordActivity
import com.tzy.demo.adapter.MainAdapter
import com.tzy.demo.bean.MainItemBean
import com.tzy.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var adapter: MainAdapter

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = MainAdapter()
        adapter.setNewInstance(getFunctionList())
        mBinding.rv.adapter = adapter
    }

    override fun initEvent() {
        adapter.setOnItemClickListener { _, _, position ->
            val itemBean: MainItemBean = adapter.getItem(position)
            startActivity(Intent(this, itemBean.target))
        }
    }

    private fun getFunctionList() = arrayListOf(
        MainItemBean("Scheme测试", SchemeTestActivity::class.java),
        MainItemBean("对象序列化测试", SerializeTestActivity::class.java),
        MainItemBean("动态壁纸", WallpaperActivity::class.java),
        MainItemBean("动画", AnimatorActivity::class.java),
        MainItemBean("DataBinding布局嵌套测试", DataBindingTestActivity::class.java),
        MainItemBean("拍照", CameraActivity::class.java),
        MainItemBean("文件下载进度", OkhttpDownloadActivity::class.java),
        MainItemBean("JS交互", TestJsActivity::class.java),
        MainItemBean("Coordinator", CoordinatorActivity::class.java),
        MainItemBean("StackView", StackViewActivity::class.java),
        MainItemBean("加载大图", LargeImageActivity::class.java),
        MainItemBean("ExoPlayer多音频同时播放", MediaPlayerActivity::class.java),
        MainItemBean("MultiRecyclerView", MultiRecyclerViewActivity::class.java),
        MainItemBean("TabLayout", TabLayoutActivity::class.java),
        MainItemBean("任务栈测试", TaskA1Activity::class.java),
        MainItemBean("Web", CommonWebviewActivity::class.java),
        MainItemBean("WebSocket", WebSocketActivity::class.java),
        MainItemBean("查看Wifi密码", WiFiPasswordActivity::class.java),
        MainItemBean("读取短信", ReadSmsActivity::class.java)
    )

}