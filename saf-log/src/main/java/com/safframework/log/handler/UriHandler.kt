package com.safframework.log.handler

import android.net.Uri
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class UriHandler:BaseHandler(), Parser<Uri> {

    override fun handle(obj: Any): Boolean {

        if (obj is Uri) {

            val s = L.getMethodNames()
            printer.println(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(uri: Uri): String {

        var msg = uri.toJavaClass() + LoggerPrinter.BR + LoggerPrinter.HORIZONTAL_DOUBLE_LINE

        return msg + JSONObject().apply {

            put("Scheme", uri.scheme)
            put("Host", uri.host)
            put("Port", uri.port)
            put("Path", uri.path)
            put("Query", uri.query)
            put("Fragment", uri.fragment)
        }
        .formatJSON()
        .let {
            it.replace("\n".toRegex(), "\n${LoggerPrinter.HORIZONTAL_DOUBLE_LINE}")
         }
    }

}