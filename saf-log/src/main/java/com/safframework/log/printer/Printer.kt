package com.safframework.log.printer

/**
 *
 * @FileName:
 *          com.safframework.log.printer.Printer
 * @author: Tony Shen
 * @date: 2019-08-15 00:26
 * @version: V1.0 <描述当前版本功能>
 */
interface Printer {

    fun println(logLevel: Int, tag: String, msg: String)
}