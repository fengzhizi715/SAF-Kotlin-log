package com.safframework.log.debugview.modules

import android.app.ActivityManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.safframework.log.debugview.base.AbstractDataModule
import com.safframework.log.debugview.base.AbstractDebugModule
import com.safframework.log.debugview.base.IViewModule
import com.safframework.log.debugview.domain.MemInfo
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.modules.MemInfoModule
 * @author: Tony Shen
 * @date: 2019-11-30 14:23
 * @version: V2.2 <描述当前版本功能>
 */
class MemInfoModule(context: Context) : AbstractDebugModule<MemInfo>(MemInfoDataModule(context, DEFAULT_INTERVAL), MemInfoViewModule()) {

    /*
    * mem info data
    * */
    private class MemInfoDataModule(context: Context, private val interval: Int) : AbstractDataModule<MemInfo>() {

        private val handler = Handler(Looper.getMainLooper())

        private val am: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        lateinit override var data: MemInfo
            private set

        private val runnable = object : Runnable {
            override fun run() {
                val systemMemInfo = ActivityManager.MemoryInfo()
                am.getMemoryInfo(systemMemInfo)
                val processMemInfo = am.getProcessMemoryInfo(intArrayOf(Process.myPid()))[0]
                data = MemInfo(systemMemInfo, processMemInfo)
                notifyUpdate()
                handler.postDelayed(this, interval.toLong())
            }
        }


        override fun start() {
            handler.post(runnable)
        }

        override fun stop() {
            handler.removeCallbacks(runnable)
        }

        override fun onReset() {

        }
    }

    /**
     * mem info view
     */
    private class MemInfoViewModule : IViewModule<MemInfo> {

        private var textView: TextView? = null

        override fun onUpdateView(data: MemInfo) {

            textView?.let {
                val builder = StringBuilder().apply{

                    append("Mem:")
                    append(DECIMAL_FORMAT.format((data.systemMemInfo.availMem / 1048576f).toDouble()))
                    append(" Pss:")
                    append(DECIMAL_FORMAT.format((data.processMemInfo.totalPss / 1024f).toDouble()))
                    append(" PD:")
                    append(DECIMAL_FORMAT.format((data.processMemInfo.totalPrivateDirty / 1024f).toDouble()))
                    append(" (MB)")
                }.toString()

                it.text = builder
            }
        }

        override fun onCreateView(root: ViewGroup): View? {
            textView = TextView(root.context).apply {
                textSize = 14f
            }

            return textView
        }

        override fun onSetVisibility(visibility: Int) {
            textView?.let {
                it.visibility = visibility
            }
        }
    }

    companion object {

        private val DEFAULT_INTERVAL = 3000
        private val DECIMAL_FORMAT = DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    }

}