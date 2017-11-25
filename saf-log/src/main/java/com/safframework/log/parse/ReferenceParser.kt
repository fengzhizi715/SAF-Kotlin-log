package com.safframework.log.parse

import com.alibaba.fastjson.JSON
import com.safframework.log.LoggerPrinter
import com.safframework.log.utils.Utils
import org.json.JSONObject
import java.lang.ref.Reference

/**
 * Created by tony on 2017/11/25.
 */
class ReferenceParser : Parser<Reference<*>> {

    override fun parseString(reference:Reference<*>): String {
        val actual = reference.get()

        var msg = reference.javaClass.canonicalName + "<" + actual?.javaClass?.simpleName + ">"+ LoggerPrinter.BR + "║ "

        val isPrimitiveType = Utils.isPrimitiveType(actual)

        if (isPrimitiveType) {

            msg += "{" + actual + "}"
        } else {

            val jsonObject = JSONObject(JSON.toJSONString(actual))
            var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            message = message.replace("\n".toRegex(), "\n║ ")
            msg += message
        }

        return msg
    }

}