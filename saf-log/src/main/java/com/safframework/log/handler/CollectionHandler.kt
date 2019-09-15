package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.isPrimitiveType
import com.safframework.log.utils.parseToJSONArray
import org.json.JSONArray

/**
 * Created by tony on 2017/11/27.
 */
class CollectionHandler:BaseHandler(),Parser<Collection<*>>{

    override fun handle(obj: Any): Boolean {

        if (obj is Collection<*>) {

            val value = obj.firstOrNull()
            val isPrimitiveType = isPrimitiveType(value)

            if (isPrimitiveType) {
                val simpleName = obj.javaClass
                var msg = "%s size = %d" + LoggerPrinter.BR
                msg = String.format(msg, simpleName, obj.size) + LoggerPrinter.HORIZONTAL_DOUBLE_LINE
                val s = L.getMethodNames()
                printer.println(LogLevel.INFO, this.logTag(),String.format(s, msg + obj.toString()))
                return true
            }

            val s = L.getMethodNames()
            printer.println(LogLevel.INFO, this.logTag(), String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(collection: Collection<*>): String {

        val jsonArray = JSONArray()

        val simpleName = collection.javaClass

        var msg = "%s size = %d" + LoggerPrinter.BR
        msg = String.format(msg, simpleName, collection.size) + LoggerPrinter.HORIZONTAL_DOUBLE_LINE

        return msg + collection.parseToJSONArray(jsonArray)
                .formatJSON()
                .let {
                    it.replace("\n".toRegex(), "\n${LoggerPrinter.HORIZONTAL_DOUBLE_LINE}")
                }
    }

}