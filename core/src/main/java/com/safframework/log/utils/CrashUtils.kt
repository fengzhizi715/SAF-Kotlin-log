package com.safframework.log.utils

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.printer.Printer
import java.lang.StringBuilder


/**
 *
 * @FileName:
 *          com.safframework.log.utils.CrashUtils
 * @author: Tony Shen
 * @date: 2021-10-14 19:51
 * @version: V1.0 <描述当前版本功能>
 */
object CrashUtils {

    private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    fun init(tag:String?, extraInfo:String?, printer: Printer, onCrashListener: OnCrashListener?) {

        Thread.setDefaultUncaughtExceptionHandler(
            getUncaughtExceptionHandler(tag, extraInfo, printer, onCrashListener)
        )
    }

    private fun getUncaughtExceptionHandler(
        tag:String?,
        extraInfo:String?,
        printer: Printer,
        onCrashListener: OnCrashListener?
    ): Thread.UncaughtExceptionHandler {
        return Thread.UncaughtExceptionHandler { t, e ->

            val sb = StringBuilder()
            sb.append(extraInfo?:"").append(getFullStackTrace(e))

            val crashInfo = sb.toString()

            L.print(LogLevel.ERROR,tag,crashInfo,printer)

            onCrashListener?.onCrash(crashInfo, e)

            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e)
        }
    }

    interface OnCrashListener {
        fun onCrash(crashInfo: String, e: Throwable)
    }
}