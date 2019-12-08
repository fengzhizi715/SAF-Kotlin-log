package com.safframework.log.converter

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 *
 * @FileName:
 *          com.safframework.log.converter.FastJSONConverter
 * @author: Tony Shen
 * @date: 2019-12-09 01:36
 * @version: V2.4 <描述当前版本功能>
 */
class FastJSONConverter : Converter {

    override fun <T> fromJson(json: String, type: Type): T {

        return JSON.parseObject(json, type)
    }

    override fun toJson(data: Any): String {

        return JSON.toJSONString(data)
    }

}