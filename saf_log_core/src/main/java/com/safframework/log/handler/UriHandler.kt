package com.safframework.log.handler

import android.net.Uri
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.formatter.Formatter
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

            L.printers().map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(uri: Uri,formatter: Formatter): String {

        var msg = uri.toJavaClass() + LoggerPrinter.BR + formatter.spliter()

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
            it.replace("\n", "\n${formatter.spliter()}")
         }
    }

}