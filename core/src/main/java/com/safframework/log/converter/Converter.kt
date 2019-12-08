package com.safframework.log.converter

import java.lang.reflect.Type

/**
 *
 * @FileName:
 *          com.safframework.log.converter.Converter
 * @author: Tony Shen
 * @date: 2019-12-08 00:36
 * @version: V2.4 <描述当前版本功能>
 */
interface Converter {

    /**
     * 将字符串转换成type类型的对象
     * @param json
     * @param type
     * @param <T>
     * @return
    </T> */
    fun <T> fromJson(json: String, type: Type): T

    /**
     * 将对象序列化成字符串对象
     * @param data
     * @return
     */
    fun toJson(data: Any): String
}