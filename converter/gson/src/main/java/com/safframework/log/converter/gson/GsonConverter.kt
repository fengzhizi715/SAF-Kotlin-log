package com.safframework.log.converter.gson

import com.safframework.log.converter.Converter
import java.lang.reflect.Type

/**
 *
 * @FileName:
 *          com.safframework.log.converter.gson.GsonConverter
 * @author: Tony Shen
 * @date: 2019-12-09 00:21
 * @version: V2.4 <描述当前版本功能>
 */
class GsonConverter : Converter {

    override fun <T> fromJson(json: String, type: Type): T {

        return GsonUtils.fromJson(json,type)
    }

    override fun toJson(data: Any): String {

        return GsonUtils.toJson(data)
    }
}