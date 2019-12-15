package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.bean.JSONConfig
import com.safframework.log.extension.formatJSON
import com.safframework.log.extension.parseToJSONArray
import com.safframework.log.formatter.Formatter
import com.safframework.log.parser.Parser
import com.safframework.log.utils.isPrimitiveType
import org.json.JSONArray

/**
 * Created by tony on 2017/11/27.
 */
class CollectionHandler:BaseHandler(),Parser<Collection<*>>{

    override fun handle(obj: Any, jsonConfig:JSONConfig): Boolean {

        if (obj is Collection<*>) {

            val value = obj.firstOrNull()
            val isPrimitiveType = isPrimitiveType(value)

            if (isPrimitiveType) {
                val simpleName = obj.javaClass
                var msg = "%s size = %d" + LoggerPrinter.BR

                jsonConfig.printers.map {
                    msg = String.format(msg, simpleName, obj.size) + it.formatter.spliter()

                    val s = L.getMethodNames(it.formatter)
                    it.printLog(jsonConfig.logLevel,jsonConfig.tag,String.format(s, msg + obj.toString()))
                }
                return true
            }

            jsonConfig.printers.map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(jsonConfig.logLevel,jsonConfig.tag, String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(collection: Collection<*>,formatter: Formatter): String {

        val jsonArray = JSONArray()

        val simpleName = collection.javaClass

        var msg = "%s size = %d" + LoggerPrinter.BR
        msg = String.format(msg, simpleName, collection.size) + formatter.spliter()

        return msg + collection.parseToJSONArray(jsonArray)
                .formatJSON()
                .let {
                    it.replace("\n", "\n${formatter.spliter()}")
                }
    }

}