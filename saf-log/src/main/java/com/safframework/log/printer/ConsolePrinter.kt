package com.safframework.log.printer

import android.util.Log
import com.safframework.log.LogLevel

/**
 *
 * @FileName:
 *          com.safframework.log.printer.ConsolePrinter
 * @author: Tony Shen
 * @date: 2019-08-15 00:51
 * @version: V1.0 <描述当前版本功能>
 */
object ConsolePrinter:Printer{

    override fun println(logLevel: LogLevel, tag: String, msg: String) {

        when(logLevel) {

            LogLevel.ERROR -> Log.e(tag, msg)

            LogLevel.WARN -> Log.w(tag, msg)

            LogLevel.INFO -> Log.i(tag, msg)

            LogLevel.DEBUG -> Log.d(tag, msg)
        }
    }

}