package com.safframework.log.debugview

import android.app.Application
import com.safframework.log.debugview.base.AbstractDebugModule
import com.safframework.log.debugview.modules.FpsModule
import com.safframework.log.debugview.modules.LogModule
import com.safframework.log.debugview.modules.MemInfoModule
import com.safframework.log.debugview.modules.TimerModule

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.DebugViewWrapper
 * @author: Tony Shen
 * @date: 2019-12-01 14:43
 * @version: V1.0 <描述当前版本功能>
 */
class DebugViewWrapper {

    private var debugView: DebugView? = null

    private object SingletonHolder {
        val instance = DebugViewWrapper()
    }

    fun init(builder: Builder?) {
        debugView = builder?.build()
    }

    fun show() {
        debugView?.show()
    }

    fun hide() {
        debugView?.uninstall()
    }

    class Builder(private val application: Application) {

        private var debugModules: MutableList<AbstractDebugModule<*>>? = null

        private var bgColor: Int = 0

        private var viewWidth: Int = 0

        private var logMaxLines: Int = 0

        private var alwaysShowOverlaySetting: Boolean = false

        init {
            // default values
            this.bgColor = 0x50005500
            this.viewWidth = 200
            this.logMaxLines = 10
            this.alwaysShowOverlaySetting = true
            this.debugModules = ArrayList<AbstractDebugModule<*>>()
        }

        fun modules(debugModules: MutableList<AbstractDebugModule<*>>): Builder {
            if (debugModules.size <= 0) {
                throw IllegalArgumentException("Module list cat not be empty")
            }
            this.debugModules = debugModules
            return this
        }

        fun modules(debugModule: AbstractDebugModule<*>, vararg otherModule: AbstractDebugModule<*>): Builder {
            this.debugModules?.clear()
            this.debugModules?.add(debugModule)
            for (module in otherModule) {
                this.debugModules?.add(module)
            }
            return this
        }

        fun bgColor(color: Int): Builder {
            this.bgColor = color
            return this
        }

        fun viewWidth(viewWidth: Int): Builder {
            this.viewWidth = viewWidth
            return this
        }

        fun logMaxLines(logMaxLines: Int): Builder {
            this.logMaxLines = logMaxLines
            return this
        }

        fun alwaysShowOverlaySetting(alwaysShowOverlaySetting: Boolean): Builder {
            this.alwaysShowOverlaySetting = alwaysShowOverlaySetting
            return this
        }

        internal fun build(): DebugView {
            if (debugModules?.size == 0) {
                debugModules?.add(MemInfoModule(application))
                debugModules?.add(FpsModule())
                debugModules?.add(TimerModule.instance)
                debugModules?.add(LogModule.instance)
                LogModule.instance.setLogMaxLines(logMaxLines)
            }
            return DebugView(application, debugModules!!,
                    DebugView.Config(bgColor, viewWidth, logMaxLines, alwaysShowOverlaySetting))
        }
    }

    companion object {

        val instance: DebugViewWrapper
            get() = SingletonHolder.instance
    }

}