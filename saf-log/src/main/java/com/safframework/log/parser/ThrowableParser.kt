package com.safframework.log.parser

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by tony on 2017/11/25.
 */
class ThrowableParser : Parser<Throwable> {

    override fun parseString(throwable: Throwable): String {

        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        throwable.printStackTrace(pw)
        pw.flush()
        var message = sw.toString()
        message = message.replace("\n".toRegex(), "\n║ ")

        return message
    }
}