package com.safframework.log.extension

import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.utils.isPrimitiveType
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * @FileName:
 *          com.safframework.log.extension.`JSONObject+Extension`
 * @author: Tony Shen
 * @date: 2019-12-08 19:42
 * @version: V2.4 <描述当前版本功能>
 */

fun JSONObject.formatJSON(): String = this.toString(LoggerPrinter.JSON_INDENT)

/**
 * 解析 bundle ，并存储到 JSONObject
 */
fun JSONObject.parseBundle(bundle: Bundle):JSONObject {

    bundle.keySet().map {

        val value = bundle.get(it)

        value?.run {
            val isPrimitiveType = isPrimitiveType(this)

            try {
                if (isPrimitiveType) {
                    put(it, bundle.get(it))
                } else {
                    put(it, JSONObject(L.getConverter()?.toJson(this)?:"{}"))
                }
            } catch (e: JSONException) {
                L.e("Invalid Json")
            }
        }
    }

    return this
}

/**
 * 解析 map ，并存储到 JSONObject
 */
fun JSONObject.parseMap(map: Map<*, *>):JSONObject {

    val keys = map.keys
    val values = map.values
    val value = values.firstOrNull()
    val isPrimitiveType = isPrimitiveType(value)

    keys.map {

        it?.let {

            try {
                if (isPrimitiveType) {
                    put(it.toString(), map[it])
                } else {
                    put(it.toString(), JSONObject(L.getConverter()?.toJson(map[it]?:"{}")?:"{}"))
                }
            } catch (e: JSONException) {
                L.e("Invalid Json")
            }
        }
    }

    return this
}