package com.safframework.log.utils

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/24.
 */

/**
 * 判断是否基本类型
 */
fun isPrimitiveType(value: Any?):Boolean = when(value){

    is Boolean -> true

    is String -> true

    is Int -> true

    is Float -> true

    is Double ->  true

    else -> false
}

fun JSONObject.parseBundle(bundle: Bundle):JSONObject {

    for (key in bundle.keySet()) {

        val isPrimitiveType = isPrimitiveType(bundle.get(key))

        try {

            if (isPrimitiveType) {
                this.put(key.toString(), bundle.get(key))
            } else {
                this.put(key.toString(), JSONObject(JSON.toJSONString(bundle.get(key))))
            }
        } catch (e: JSONException) {
            L.e("Invalid Json")
        }
    }

    return this
}

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
                this.put(it.toString(), JSONObject(JSON.toJSONString(map.get(it))))
            }
        } catch (e: JSONException) {
            L.e("Invalid Json")
        }
    }

    return this
}
