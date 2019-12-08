package com.safframework.log.converter.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 *
 * @FileName:
 *          com.safframework.log.converter.gson.GsonUtils
 * @author: Tony Shen
 * @date: 2019-12-09 00:30
 * @version: V1.0 <描述当前版本功能>
 */
object GsonUtils {

    private var gson: Gson

    init {
        gson = Gson()
    }

    @JvmStatic
    fun <T> fromJson(json: String, type: Type): T {

        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun <T> fromJson(jsonElement: JsonElement, type: Type): T {

        return gson.fromJson(jsonElement, type)
    }

    @JvmStatic
    fun toJson(data: Any): String {

        return gson.toJson(data)
    }
}