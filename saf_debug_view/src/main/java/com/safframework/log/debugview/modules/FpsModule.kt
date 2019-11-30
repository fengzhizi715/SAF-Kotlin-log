package com.safframework.log.debugview.modules

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.safframework.log.debugview.base.AbstractDataModule
import com.safframework.log.debugview.base.AbstractDebugModule
import com.safframework.log.debugview.base.IViewModule
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.modules.FpsModule
 * @author: Tony Shen
 * @date: 2019-11-30 14:09
 * @version: V1.0 <描述当前版本功能>
 */
class FpsModule : AbstractDebugModule<Double>(FpsDataModule(DEFAULT_INTERVAL), FpsViewModule()) {

    /*
    * fps data
    * */
    private class FpsDataModule internal constructor(interval: Int) : AbstractDataModule<Double>() {

        private val handler = Handler(Looper.getMainLooper())

        private var startFrameTimeMillis: Long = 0
        private var numFramesRendered: Int = 0

        private var fps: Double = 0.toDouble()

        private var frameCallback: Choreographer.FrameCallback? = null

        override val data: Double
            get() = fps

        init {
            handler.post {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    frameCallback = object : Choreographer.FrameCallback {
                        override fun doFrame(frameTimeNanos: Long) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                val currentFrameTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)

                                if (startFrameTimeMillis > 0) {
                                    val duration = currentFrameTimeMillis - startFrameTimeMillis
                                    numFramesRendered++

                                    if (duration > interval) {
                                        fps = (numFramesRendered * 1000f / duration).toDouble()

                                        notifyUpdate()

                                        startFrameTimeMillis = currentFrameTimeMillis
                                        numFramesRendered = 0
                                    }
                                } else {
                                    startFrameTimeMillis = currentFrameTimeMillis
                                }

                                Choreographer.getInstance().postFrameCallback(this)
                            }
                        }
                    }
                }
            }
        }


        override fun start() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                handler.post {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Choreographer.getInstance().postFrameCallback(frameCallback)
                    }
                }

            }
        }

        override fun stop() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                handler.post {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Choreographer.getInstance().removeFrameCallback(frameCallback)
                    }
                }


            }
        }

        override fun onReset() {

        }
    }

    /*
    * fps view
    * */
    private class FpsViewModule : IViewModule<Double> {

        private var fpsTxtView: TextView? = null

        override fun onCreateView(root: ViewGroup): View? {

            fpsTxtView = TextView(root.context).apply {
                textSize = 14f
            }

            return fpsTxtView
        }

        override fun onUpdateView(data: Double) {
            fpsTxtView?.let {
                it.text = DECIMAL_FORMAT.format(data)
            }
        }

        override fun onSetVisibility(visibility: Int) {
            fpsTxtView?.let {
                it.visibility = visibility
            }
        }

    }

    companion object {

        private val DEFAULT_INTERVAL = 2000
        private val DECIMAL_FORMAT = DecimalFormat("'FPS:' 0.0",
                DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    }

}