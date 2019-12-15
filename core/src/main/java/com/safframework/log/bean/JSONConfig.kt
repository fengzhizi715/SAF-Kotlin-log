package com.safframework.log.bean

import com.safframework.log.LogLevel
import com.safframework.log.printer.Printer

/**
 *
 * @FileName:
 *          com.safframework.log.bean.JSONConfig
 * @author: Tony Shen
 * @date: 2019-12-14 13:47
 * @version: V2.4 <描述当前版本功能>
 */
data class JSONConfig(val logLevel: LogLevel = LogLevel.INFO, val tag:String, val printers:MutableSet<Printer>)