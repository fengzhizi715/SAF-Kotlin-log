package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.parser.CollectionParser
import com.safframework.log.utils.Utils

/**
 * Created by tony on 2017/11/27.
 */
class CollectionHandler():BaseHandler(){

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
            val parser = CollectionParser()
            println(String.format(s, parser.parseString(obj)))

            return true
        }

        return false
    }

}