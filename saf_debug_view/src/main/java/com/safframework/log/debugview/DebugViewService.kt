package com.safframework.log.debugview

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import com.safframework.log.L
import com.safframework.log.debugview.base.AbstractDebugModule

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.DebugViewService
 * @author: Tony Shen
 * @date: 2019-11-30 15:18
 * @version: V2.2 <描述当前版本功能>
 */
internal class DebugViewService : Service() {

    private val binder = LocalBinder()
    private var debugModules: List<AbstractDebugModule<*>> = ArrayList()
    private var debugViewManager: DebugViewManager? = null
    private var modulesStarted: Boolean = false

    internal inner class LocalBinder : Binder() {
        val service: DebugViewService
            get() = this@DebugViewService
    }

    override fun onCreate() {
        L.i("service onCreate()")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // no need to restart this service
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        L.i("service onDestroy()")
        stopModules()
        debugViewManager?.hideDebugView()
    }

    override fun onBind(intent: Intent) = binder

    override fun onTaskRemoved(rootIntent: Intent) {
        L.i("service onTaskRemoved()")
        stopSelf()
    }

    fun setDebugModules(debugModules: List<AbstractDebugModule<*>>) {
        this.debugModules = debugModules
    }

    internal fun setDebugViewManager(debugViewManager: DebugViewManager?) {
        this.debugViewManager = debugViewManager
        debugViewManager?.showDebugView()
    }

    fun startModules() {
        if (!modulesStarted) {
            for (debugModule in debugModules) {
                debugModule.start()
            }
            modulesStarted = true
        }
    }

    fun stopModules() {
        if (modulesStarted) {
            for (debugModule in debugModules) {
                debugModule.stop()
            }
            modulesStarted = false
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, DebugViewService::class.java)
        }
    }

}