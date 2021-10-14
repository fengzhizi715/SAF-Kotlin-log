package cn.salesuite.saf.kotlin.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.safframework.log.configL
import com.safframework.log.converter.gson.GsonConverter
import com.safframework.log.debugview.DebugViewWrapper
import com.safframework.log.debugview.modules.TimerModule
import com.safframework.log.printer.FilePrinter
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.utils.CrashUtils

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.app.App
 * @author: Tony Shen
 * @date: 2019-12-01 15:23
 * @version: V1.0 <描述当前版本功能>
 */

@SuppressLint("StaticFieldLeak")
var application: Application? = null

class App : Application() {

    private var crashPrinter: FilePrinter

    init{
        crashPrinter = FileBuilder().folderPath("/storage/emulated/0/crash_logs").build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
        TimerModule.begin(this)
    }

    override fun onCreate() {
        super.onCreate()

        configL { // 使用自定义的 DSL 来配置 L

            converter = GsonConverter()
        }

        CrashUtils.init(tag = "crashTag",printer = crashPrinter, onCrashListener = object : CrashUtils.OnCrashListener {
            override fun onCrash(crashInfo: String, e: Throwable) {

            }
        })

        initDebugView()
    }

    // 方便调试的时候，将产生的日志展示到一个特定控件上，看是否有必要使用
    private fun initDebugView() {
        DebugViewWrapper.init(
                DebugViewWrapper.Builder(this)
                        .viewWidth(250) /* the width of debug-view */
                        .bgColor(0x6f677700) /* the color of debug-view */
                        .alwaysShowOverlaySetting(true) /* the flag for always showing Overlay Setting */
                        .logMaxLines(50) /* the max lines of log */
        )

        DebugViewWrapper.show()
    }
}