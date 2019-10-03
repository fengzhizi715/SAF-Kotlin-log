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
class StringHandler:BaseHandler(), Parser<String> {

    companion object {
        private const val MAX_STRING_LENGTH = 4000
    }

    override fun handle(obj: Any): Boolean {

        if (obj is String) {

            var json = obj.trim { it <= ' ' }

            L.printers().map {
                val s = L.getMethodNames(it.formatter)
                val logString = String.format(s, parseString(json,it.formatter))
                printLongLog(LogLevel.INFO,this.logTag(),logString,it)
            }
            return true
        }

        return false
    }

    /**
     * 打印超过4000行的日志
     */
    private fun printLongLog(logLevel: LogLevel,tag: String, logString: String, printer:Printer) {

        if (logString.length > MAX_STRING_LENGTH) {

            var i = 0

            while (i < logString.length) {

                if (i + MAX_STRING_LENGTH < logString.length) {
                    if (i==0) {
                        printer.printLog(logLevel, tag, logString.substring(i, i + MAX_STRING_LENGTH))
                    } else {
                        printer.printLog(logLevel, "", logString.substring(i, i + MAX_STRING_LENGTH))
                    }
                } else
                    printer.printLog(logLevel, "", logString.substring(i, logString.length))

                i += MAX_STRING_LENGTH
            }
        } else
            printer.printLog(logLevel, tag, logString)
    }

    override fun parseString(json: String,formatter: Formatter): String {
        var message = ""

        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.formatJSON()
                message = message.replace("\n", "\n${formatter.spliter()}")
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.formatJSON()
                message = message.replace("\n", "\n${formatter.spliter()}")
            } else { // 普通的字符串
                message = json.replace("\n", "\n${formatter.spliter()}")
            }
        } catch (e: JSONException) {
            L.e("Invalid Json: $json")
            message = ""
        }

        return message
    }

}