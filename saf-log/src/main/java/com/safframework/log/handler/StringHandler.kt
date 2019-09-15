package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.printer.Printer
import com.safframework.log.utils.formatJSON
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class StringHandler(printer: Printer, formatter: Formatter):BaseHandler(printer,formatter), Parser<String> {

    override fun handle(obj: Any): Boolean {

        if (obj is String) {

            var json = obj.trim { it <= ' ' }
            val s = L.getMethodNames()
            println(LogLevel.INFO, this.logTag(),String.format(s, parseString(json)))
            return true
        }

        return false
    }

    override fun parseString(json: String): String {
        var message: String = ""

        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.formatJSON()
                message = message.replace("\n".toRegex(), "\n${spliter()}")
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.formatJSON()
                message = message.replace("\n".toRegex(), "\n${spliter()}")
            }
        } catch (e: JSONException) {
            L.e("Invalid Json: $json")
            message = ""
        }

        return message
    }

}