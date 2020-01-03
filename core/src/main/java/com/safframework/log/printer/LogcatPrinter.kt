package com.safframework.log.printer

import android.util.Log
import com.safframework.log.LogLevel
import com.safframework.log.formatter.BorderFormatter
import com.safframework.log.formatter.Formatter

/**
 *
 * @FileName:
 *          com.safframework.log.printer.LogcatPrinter
 * @author: Tony Shen
 * @date: 2019-08-15 00:51
 * @since: V2.0 打印到 Logcat 的Printer
 */
class LogcatPrinter(override val formatter: Formatter = BorderFormatter):Printer {

    override fun printLog(logLevel: LogLevel, tag: String, msg: String) {

        when(logLevel) {

            LogLevel.ERROR -> Log.e(tag, msg)

            LogLevel.WARN  -> Log.w(tag, msg)

            LogLevel.INFO  -> Log.i(tag, msg)

            LogLevel.DEBUG -> Log.d(tag, msg)
        }
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogcatPrinter

        if (formatter != other.formatter) return false

        return true
    }

    override fun hashCode() = formatter.hashCode()
}