package com.tzy.demo.activity.wallpaper

import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityBox2dBinding

class Box2dActivity : BaseActivity<ActivityBox2dBinding>(), AndroidFragmentApplication.Callbacks {
    override fun getLayoutId(): Int {
        return R.layout.activity_box2d
    }

    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.fl_container, Box2dFragment()).commit()
    }

    override fun initEvent() {

    }

    override fun exit() {

    }

    override fun isFullScreen(): Boolean {
        return true
    }
}