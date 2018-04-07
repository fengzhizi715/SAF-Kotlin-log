package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.parser.Parser
import com.safframework.log.utils.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class CollectionHandler:BaseHandler(),Parser<Collection<*>>{

    override fun handle(obj: Any): Boolean {

        if (obj is Collection<*>) {

            val value = obj.firstOrNull()
            val isPrimitiveType = Utils.isPrimitiveType(value)

            if (isPrimitiveType) {
                val simpleName = obj.javaClass
                var msg = "%s size = %d" + LoggerPrinter.BR
                msg = String.format(msg, simpleName, obj.size) + "║ "
                val s = L.getMethodNames()
                println(String.format(s, msg + obj.toString()))
                return true
            }

            val s = L.getMethodNames()
            println(String.format(s, parseString(obj)))

            return true
        }

        return false
    }

    override fun parseString(collection: Collection<*>): String {

        val jsonArray = JSONArray()

        val simpleName = collection.javaClass

        var msg = "%s size = %d" + LoggerPrinter.BR
        msg = String.format(msg, simpleName, collection.size) + "║ "

        collection.map {

            it ->

            try {
                val objStr = JSON.toJSONString(it)
                val jsonObject = JSONObject(objStr)
                jsonArray.put(jsonObject)
            } catch (e: JSONException) {
                L.e("Invalid Json")
            }
        }

        var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

}