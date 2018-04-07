package com.safframework.log.handler

import android.net.Uri
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.parser.Parser
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class UriHandler:BaseHandler(), Parser<Uri> {

    override fun handle(obj: Any): Boolean {

        if (obj is Uri) {

            val s = L.getMethodNames()
            println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(uri: Uri): String {

        var msg = uri.javaClass.toString() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject()
        jsonObject.put("Scheme", uri.scheme)
        jsonObject.put("Host", uri.host)
        jsonObject.put("Port", uri.port)
        jsonObject.put("Path", uri.path)
        jsonObject.put("Query", uri.query)
        jsonObject.put("Fragment", uri.fragment)

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

}