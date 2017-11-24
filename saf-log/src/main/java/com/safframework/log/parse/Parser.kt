package com.safframework.log.parse


/**
 * Created by tony on 2017/11/24.
 */
interface Parser<T> {

    fun parseClassType(): Class<T>

    fun parseString(t: T): String
}