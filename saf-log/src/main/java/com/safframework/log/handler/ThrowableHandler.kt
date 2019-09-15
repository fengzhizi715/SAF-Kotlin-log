package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.L.formatter
import com.safframework.log.L.printer
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.printer.Printer
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by tony on 2017/11/27.
 */
class ThrowableHandler(printer: Printer, formatter: Formatter):BaseHandler(printer,formatter), Parser<Throwable> {

    override fun handle(obj: Any): Boolean {

        if (obj is Throwable) {

            val s = L.getMethodNames()
            printer.println(LogLevel.ERROR, this.logTag(),String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(throwable: Throwable): String {
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        throwable.printStackTrace(pw)
        pw.flush()
        var message = sw.toString()
        message = message.replace("\n".toRegex(), "\n${formatter.spliter()}")

        return message
    }

}