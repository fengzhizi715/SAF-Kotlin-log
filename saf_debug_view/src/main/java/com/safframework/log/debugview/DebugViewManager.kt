package com.safframework.log.debugview

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.view.WindowManager.LayoutParams
import android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
import android.view.WindowManager.LayoutParams.TYPE_TOAST

import com.safframework.log.debugview.base.AbstractDebugModule
import com.safframework.log.debugview.extension.dp2px

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.DebugViewManager
 * @author: Tony Shen
 * @date: 2019-11-30 15:23
 * @version: V1.0 <描述当前版本功能>
 */
internal class DebugViewManager(private val context: Context, private val config: DebugView.Config) {

    private val windowManager: WindowManager
    private var rootView: ViewGroup? = null
    private var controlView: View? = null
    private var debugModules: List<AbstractDebugModule<*>>? = null
    private val handler = Handler(Looper.getMainLooper())

    private val windowTypeForOverlay: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            TYPE_SYSTEM_ALERT
        } else {
            TYPE_TOAST
        }

    init {
        this.windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun setDebugModules(debugModules: List<AbstractDebugModule<*>>) {
        this.debugModules = debugModules
    }

    fun showDebugView() {
        handler.post {
            if (rootView == null) {
                rootView = createRoot()

                for (debugModule in debugModules!!) {
                    val view = debugModule.createView(rootView!!)
                    if (view?.parent == null) {
                        rootView?.addView(view)
                    }
                }
                val layoutParamsWidth = context.dp2px(config.viewWidth)
                val params = createDebugLayoutParams(layoutParamsWidth)
                windowManager.addView(rootView, params)

                initControlView()
            }
        }
    }

    private fun initControlView() {
        controlView = View(context).apply {

            setBackgroundColor(config.bgColor)
            val d = context.dp2px(18)
            windowManager.addView(this, createControlLayoutParams(d, d))
            setOnClickListener {
                for (debugModule in debugModules!!) {
                    debugModule.reset()
                }
            }
            setOnLongClickListener {
                rootView?.let {
                    if (it.visibility == View.GONE) {
                        it.visibility = View.VISIBLE
                        for (debugModule in debugModules!!) {
                            debugModule.start()
                        }
                    } else {
                        it.visibility = View.GONE
                        for (debugModule in debugModules!!) {
                            debugModule.stop()
                        }
                    }
                }
                true
            }
        }

    }


    fun hideDebugView() {
        handler.post {
            rootView?.let {
                windowManager.removeView(rootView)
                rootView = null
            }
            controlView?.let{
                windowManager.removeView(controlView)
                controlView = null
            }
        }
    }

    private fun createRoot(): ViewGroup {

        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            if (config.bgColor != Color.TRANSPARENT) {
                setBackgroundColor(config.bgColor)
            }
        }
    }

    private fun createDebugLayoutParams(width: Int) = WindowManager.LayoutParams().apply {
        this.width = width
        this.height = WindowManager.LayoutParams.WRAP_CONTENT
        this.type = windowTypeForOverlay
        this.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        this.format = PixelFormat.TRANSLUCENT
        this.gravity = Gravity.TOP or Gravity.END
    }

    private fun createControlLayoutParams(width: Int, height: Int) = WindowManager.LayoutParams().apply {
        this.width = width
        this.height = height
        this.type = windowTypeForOverlay
        this.format = PixelFormat.TRANSLUCENT
        this.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        this.gravity = Gravity.BOTTOM or Gravity.START
    }
}