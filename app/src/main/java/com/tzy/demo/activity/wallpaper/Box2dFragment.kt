package com.tzy.demo.activity.wallpaper

import android.content.Context
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.PowerManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.tzy.demo.R

class Box2dFragment : AndroidFragmentApplication() {

    private lateinit var rootView: View
    private lateinit var _container: FrameLayout
    private var box2dEffectView: Box2dEffectView? = null
    private var powerManager: PowerManager? = null

    companion object {
        const val TAG = "Box2dFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_box2d, null)
        _container = rootView.findViewById(R.id.fl_container)
        powerManager = activity?.getSystemService(Context.POWER_SERVICE) as PowerManager
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildGDX()

        rootView.findViewById<Button>(R.id.bt_create).setOnClickListener {
            generateBody()
        }
    }

    override fun onStart() {
        super.onStart()
        box2dEffectView?.setCanDraw(true)
    }

    override fun onStop() {
        super.onStop()
        box2dEffectView?.setCanDraw(false)
    }

    override fun onConfigurationChanged(config: Configuration) {
        super.onConfigurationChanged(config)

        cleanGDX()
        buildGDX()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        box2dEffectView?.release()
    }

    private fun buildGDX() {
        box2dEffectView = Box2dEffectView(activity!!)
        val configuration = AndroidApplicationConfiguration().apply {
            a = 8
        }
        val view = initializeForView(box2dEffectView, configuration)
        if (view is GLSurfaceView) {
            view.holder.setFormat(PixelFormat.TRANSPARENT)
            view.setZOrderMediaOverlay(true)
            view.setZOrderOnTop(true)
        }
        _container.addView(view)
    }

    private fun cleanGDX() {
        box2dEffectView?.let {
            it.dispose()
            box2dEffectView = null
        }
        _container.removeAllViews()
    }

    private fun generateBody() {
        Thread {
            for (i in 0..12) {
                Thread.sleep(50)
                runOnUiThread { box2dEffectView?.addBody( i % 2 == 0) }
            }
        }.start()
    }

}