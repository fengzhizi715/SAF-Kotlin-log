package cn.salesuite.saf.kotlin.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.safframework.log.configL
import com.safframework.log.converter.gson.GsonConverter
import com.safframework.log.debugview.DebugViewWrapper
import com.safframework.log.debugview.modules.TimerModule

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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
        TimerModule.begin(this)
    }

    override fun onCreate() {
        super.onCreate()

        configL {

            converter = GsonConverter()
        }

        initDebugView()
    }

    private fun initDebugView() {
        DebugViewWrapper.init(
                DebugViewWrapper.Builder(this)
                        .viewWidth(250) /* the width of debug-view */
                        .bgColor(0x6f677700) /* the color of debug-view */
                        .alwaysShowOverlaySetting(true) /* the flag for always showing Overlay Setting */
                        .logMaxLines(20) /* the max lines of log */
        )

        DebugViewWrapper.show()
    }
}