package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by tony on 2017/11/27.
 */
class ThrowableHandler:BaseHandler(), Parser<Throwable> {

    override fun handle(obj: Any): Boolean {

        if (obj is Throwable) {

            L.printers().map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(LogLevel.ERROR, this.logTag(),String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(throwable: Throwable,formatter: Formatter): String {
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        throwable.printStackTrace(pw)
        pw.flush()
        var message = sw.toString()
        message = message.replace("\n", "\n${formatter.spliter()}")

        return message
    }

}