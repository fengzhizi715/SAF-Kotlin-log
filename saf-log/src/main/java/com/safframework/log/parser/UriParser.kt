package com.safframework.log.parser

import android.net.Uri
import com.safframework.log.LoggerPrinter
import org.json.JSONObject

/**
 * Created by tony on 2017/11/25.
 */
class UriParser : Parser<Uri> {

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