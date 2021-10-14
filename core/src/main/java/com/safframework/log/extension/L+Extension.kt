package com.safframework.log.extension

import com.safframework.log.L
import com.safframework.log.bean.JSONConfig

/**
 * Created by Tony Shen on 2017/6/30.
 */
inline fun <reified T> T.logTag() = T::class.javaClass.name

inline fun <reified T> Class<T>.logTag() = simpleName


fun String?.e() = L.e(this)

fun String?.w() = L.w(this)

fun String?.i() = L.i(this)

fun String?.d() = L.d(this)

fun Any?.json()  = L.json(this)


inline fun L.e(msg: msgFunction)  = e(msg.invoke())

inline fun L.e(tag: String?, msg: msgFunction) = e(tag, msg.invoke())

inline fun L.w(msg: msgFunction) = w(msg.invoke())

inline fun L.w(tag: String?, msg: msgFunction) = w(tag, msg.invoke())

inline fun L.i(msg: msgFunction) = i(msg.invoke())

inline fun L.i(tag: String?, msg: msgFunction) = i(tag, msg.invoke())

inline fun L.d(msg: msgFunction) = d(msg.invoke())

inline fun L.d(tag: String?, msg: msgFunction) = d(tag, msg.invoke())

inline fun L.json(any:anyFunction)  = json(any.invoke())

inline fun L.json(any:anyFunction,jsonConfig: JSONConfig)  = json(any.invoke(),jsonConfig)