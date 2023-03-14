package com.tzy.demo.activity.mvvm

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityDatabindingTestBinding


class DataBindingTestActivity : BaseActivity<ActivityDatabindingTestBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_databinding_test
    }

    override fun initView() {
        val binding1 = mBinding.binding1
        val binding2 = mBinding.binding2

        binding1.ll1.setOnClickListener {
            Toast.makeText(this, binding1.tv1.text, Toast.LENGTH_SHORT).show()
        }

        binding2.ll2.setOnClickListener {
            Toast.makeText(this, binding2.tv2.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun initEvent() {
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val outPoint = Point()
        display.getRealSize(outPoint)
        Log.e("aa","x=${outPoint.x}  y=${outPoint.y}")
        display.getSize(outPoint)
        Log.e("aa","x=${outPoint.x}  y=${outPoint.y}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}