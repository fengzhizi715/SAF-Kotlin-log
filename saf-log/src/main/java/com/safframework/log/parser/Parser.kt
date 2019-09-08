package com.safframework.log.parser


/**
 * 将对象解析成特定的字符串
 * Created by tony on 2017/11/24.
 */
interface Parser<T> {

    fun parseString(t: T): String
}