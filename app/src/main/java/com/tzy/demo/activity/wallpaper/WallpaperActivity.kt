package com.tzy.demo.activity.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityWallpaperBinding


class WallpaperActivity : BaseActivity<ActivityWallpaperBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_wallpaper
    }

    override fun initView() {

    }

    override fun initEvent() {
        mBinding.btParticleEffects.setOnClickListener {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, ParticleEffectsWallpaper::class.java)
            )
            startActivityForResult(intent, 1)
        }

        mBinding.btBox2d.setOnClickListener {
            startActivity(Intent(this, Box2dActivity::class.java))
        }
    }
}