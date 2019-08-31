package com.safframework.log.printer

import com.safframework.log.LogLevel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


/**
 *
 * @FileName:
 *          com.safframework.log.printer.FilePrinter
 * @author: Tony Shen
 * @date: 2019-08-31 10:58
 * @version: V1.0 <描述当前版本功能>
 */
object FilePrinter:Printer{

    private val channel = Channel<LogItem>()

    init {
        GlobalScope.launch {
            channel.consumeEach {

                doPrintln(it.timeMillis,it.level,it.tag,it.msg)
            }
        }
    }

    override fun println(logLevel: LogLevel, tag: String, msg: String) {

        val timeMillis = System.currentTimeMillis()

        GlobalScope.launch {
            channel.send(LogItem(timeMillis, logLevel, tag, msg))
        }
    }

    private fun doPrintln(timeMillis: Long, logLevel: LogLevel, tag: String, msg: String) {

    }

    private class LogItem internal constructor(internal var timeMillis: Long, internal var level: LogLevel, internal var tag: String, internal var msg: String)

}