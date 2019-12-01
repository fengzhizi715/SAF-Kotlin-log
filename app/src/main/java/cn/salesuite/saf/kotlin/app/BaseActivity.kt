package cn.salesuite.saf.kotlin.app

import android.app.Activity
import android.os.Bundle
import com.safframework.log.debugview.modules.TimerModule

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.app.BaseActivity
 * @author: Tony Shen
 * @date: 2019-12-01 15:37
 * @version: V1.0 <描述当前版本功能>
 */
open class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TimerModule.instance.begin(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            TimerModule.instance.end(this)
        }
    }
}