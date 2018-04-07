package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.parser.Parser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class StringHandler:BaseHandler(), Parser<String> {

    override fun handle(obj: Any): Boolean {

        if (obj is String) {

            var json = obj.trim { it <= ' ' }
            val s = L.getMethodNames()
            println(String.format(s, parseString(json)))
            return true
        }

        return false
    }

    override fun parseString(json: String): String {
        var message: String = ""

        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
            }
        } catch (e: JSONException) {
            L.e("Invalid Json: " + json)
            message = ""
        }

        return message
    }

}