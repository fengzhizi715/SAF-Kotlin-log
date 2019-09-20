package com.safframework.log.utils

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/24.
 */

/**
 * 判断 Any 是否为基本类型
 */
fun isPrimitiveType(value: Any?) = when(value){

    is Boolean -> true

    is String -> true

    is Int -> true

    is Float -> true

    is Double ->  true

    else -> false
}

fun Any.toJavaClass()        = this.javaClass.toString()

fun JSONObject.formatJSON() = this.toString(LoggerPrinter.JSON_INDENT)

fun JSONArray.formatJSON()  = this.toString(LoggerPrinter.JSON_INDENT)

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

/**
 * 解析 collection ，并存储到 JSONArray
 */
fun Collection<*>.parseToJSONArray(jsonArray: JSONArray):JSONArray {

    this.map {

        try {
            val objStr = JSON.toJSONString(it)
            val jsonObject = JSONObject(objStr)
            jsonArray.put(jsonObject)
        } catch (e: JSONException) {
            L.e("Invalid Json")
        }
    }

    return jsonArray
}
