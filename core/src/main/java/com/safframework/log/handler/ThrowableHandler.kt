package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.bean.JSONConfig
import com.safframework.log.formatter.Formatter
import com.safframework.log.parser.Parser
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by tony on 2017/11/27.
 */
class ThrowableHandler:BaseHandler(), Parser<Throwable> {

    override fun handle(obj: Any, jsonConfig: JSONConfig): Boolean {

        if (obj is Throwable) {

            jsonConfig.printers.map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(jsonConfig.logLevel,jsonConfig.tag,String.format(s, parseString(obj,it.formatter)))
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