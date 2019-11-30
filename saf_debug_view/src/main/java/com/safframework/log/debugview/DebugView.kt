package com.safframework.log.debugview

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings

import com.safframework.log.debugview.base.AbstractDebugModule

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.DebugView
 * @author: Tony Shen
 * @date: 2019-11-30 15:13
 * @version: V1.0 <描述当前版本功能>
 */
internal class DebugView(private val application: Application, private val debugModules: List<AbstractDebugModule<*>>, private val config: Config) {

    private var debugViewService: DebugViewService? = null

    private var debugViewManager: DebugViewManager? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as DebugViewService.LocalBinder
            debugViewService = binder.service.apply {
                setDebugModules(debugModules)
                setDebugViewManager(debugViewManager)
                startModules()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(application)) {
                if (!config.isAlwaysShowOverlaySetting) {
                    val sp = application.getSharedPreferences("debug_view_config", Context.MODE_PRIVATE)
                    if (sp.getBoolean("is_showed_overlay_setting", false)) {
                        return
                    }
                    sp.edit().putBoolean("is_showed_overlay_setting", true).apply()
                }
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + application.packageName))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                application.startActivity(intent)
            }
        }
        debugViewManager = DebugViewManager(application, config).apply {
            setDebugModules(debugModules)
        }
        startAndBindDebugService()
    }

    fun uninstall() {
        unbindFromDebugService()
        application.stopService(DebugViewService.createIntent(application))
    }

    private fun startAndBindDebugService() {
        val intent = Intent(application, DebugViewService::class.java)
        application.startService(intent)
        bindToDebugViewService()
    }

    private fun bindToDebugViewService() {
        val bound = application.bindService(DebugViewService.createIntent(application),
                serviceConnection, Context.BIND_AUTO_CREATE)
        if (!bound) {
            throw RuntimeException("Could not bind the DebugOverlayService")
        }
    }

    private fun unbindFromDebugService() {

        debugViewService?.let {
            application.unbindService(serviceConnection)
            debugViewService = null
        }
    }


    internal class Config(val bgColor: Int, val viewWidth: Int, val logMaxLines: Int, val isAlwaysShowOverlaySetting: Boolean)
}