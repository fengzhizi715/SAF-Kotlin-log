package com.safframework.log.extension

import android.os.Bundle
import com.alibaba.fastjson.JSON
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

        val isPrimitiveType = isPrimitiveType(bundle.get(it))

        try {
            if (isPrimitiveType) {
                this.put(it, bundle.get(it))
            } else {
                this.put(it, JSONObject(JSON.toJSONString(bundle.get(it))))
            }
        } catch (e: JSONException) {
            L.e("Invalid Json")
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

        try {
            if (isPrimitiveType) {
                this.put(it.toString(), map[it])
            } else {
                this.put(it.toString(), JSONObject(JSON.toJSONString(map[it])))
            }
        } catch (e: JSONException) {
            L.e("Invalid Json")
        }
    }

    return this
}