package com.safframework.log.printer

import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter

/**
 *
 * @FileName:
 *          com.safframework.log.printer.Printer
 * @author: Tony Shen
 * @date: 2019-08-15 00:26
 * @version: V1.0 打印日志，支持打印日志输出到 Console、File
 */
interface Printer {

    val formatter: Formatter

    fun println(logLevel: LogLevel, tag: String, msg: String)
}