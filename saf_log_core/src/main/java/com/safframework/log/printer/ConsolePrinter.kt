package com.safframework.log.printer

import android.util.Log
import com.safframework.log.LogLevel
import com.safframework.log.formatter.BorderFormatter
import com.safframework.log.formatter.Formatter

/**
 *
 * @FileName:
 *          com.safframework.log.printer.ConsolePrinter
 * @author: Tony Shen
 * @date: 2019-08-15 00:51
 * @version: V1.0 打印到控制台的Printer
 */
class ConsolePrinter(override val formatter: Formatter = BorderFormatter()):Printer {

    override fun printLog(logLevel: LogLevel, tag: String, msg: String) {

        when(logLevel) {

            LogLevel.ERROR -> Log.e(tag, msg)

            LogLevel.WARN -> Log.w(tag, msg)

            LogLevel.INFO -> Log.i(tag, msg)

            LogLevel.DEBUG -> Log.d(tag, msg)
        }
    }

}