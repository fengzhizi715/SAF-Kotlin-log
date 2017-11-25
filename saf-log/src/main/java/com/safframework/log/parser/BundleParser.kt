package com.safframework.log.parser

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.utils.Utils
import org.json.JSONException
import org.json.JSONObject


/**
 * Created by tony on 2017/11/25.
 */
class BundleParser : Parser<Bundle> {

    override fun parseString(bundle: Bundle): String {

        var msg = bundle.javaClass.toString() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject()
        for (key in bundle.keySet()) {

            val isPrimitiveType = Utils.isPrimitiveType(bundle.get(key))

            try {

                if (isPrimitiveType) {
                    jsonObject.put(key.toString(), bundle.get(key))
                } else {
                    jsonObject.put(key.toString(), JSONObject(JSON.toJSONString(bundle.get(key))))
                }
            } catch (e: JSONException) {
                L.e("Invalid Json")
            }

        }

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

}