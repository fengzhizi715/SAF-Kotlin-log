package com.safframework.log.debugview.modules

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.safframework.log.debugview.base.AbstractDataModule
import com.safframework.log.debugview.base.AbstractDebugModule
import com.safframework.log.debugview.base.IViewModule
import com.safframework.log.debugview.utils.support
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
 * @version: V2.2 <描述当前版本功能>
 */
object FpsModule : AbstractDebugModule<Double>(FpsDataModule(), FpsViewModule()) {

    private val DEFAULT_INTERVAL = 2000
    private val DECIMAL_FORMAT = DecimalFormat("'FPS:' 0.0",
            DecimalFormatSymbols.getInstance(Locale.ENGLISH))

    /*
    * fps data
    * */
    private class FpsDataModule internal constructor(interval: Int=DEFAULT_INTERVAL) : AbstractDataModule<Double>() {

        private val handler = Handler(Looper.getMainLooper())

        private var startFrameTimeMillis: Long = 0
        private var numFramesRendered: Int = 0

        private var fps: Double = 0.toDouble()

        private var frameCallback: Choreographer.FrameCallback? = null

        override val data: Double
            get() = fps

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                handler.post {

                    frameCallback = object : Choreographer.FrameCallback {
                        override fun doFrame(frameTimeNanos: Long) {

                            support(Build.VERSION_CODES.JELLY_BEAN) {
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


        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun start() {

            support (Build.VERSION_CODES.JELLY_BEAN) {
                handler.post {
                    Choreographer.getInstance().postFrameCallback(frameCallback)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun stop() {

            support(Build.VERSION_CODES.JELLY_BEAN) {
                handler.post {
                    Choreographer.getInstance().removeFrameCallback(frameCallback)
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
}