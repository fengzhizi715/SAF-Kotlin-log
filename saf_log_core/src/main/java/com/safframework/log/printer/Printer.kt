package com.safframework.log.printer

import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter

/**
 *
 * @FileName:
 *          com.safframework.log.printer.Printer
 * @author: Tony Shen
 * @date: 2019-08-15 00:26
 * @since: V2.0 打印日志，支持打印日志输出到 Console、File 等等
 */
interface Printer {

    val formatter: Formatter // 用于格式化日志

    /**
     * 打印日志
     */
    fun printLog(logLevel: LogLevel, tag: String, msg: String)
}