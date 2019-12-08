package com.safframework.log.extension

import com.safframework.log.L

/**
 * Created by Tony Shen on 2017/6/30.
 */
typealias msgFunction = () -> String


inline fun <reified T> T.logTag() = T::class.java.simpleName

inline fun <reified T> Class<T>.logTag() = simpleName


fun String?.e() = L.e(this)

fun String?.w() = L.w(this)

fun String?.i() = L.i(this)

fun String?.d() = L.d(this)

fun Any?.json()  = L.json(this)


inline fun L.e(msg: msgFunction)  = e(msg.invoke())

inline fun L.e(tag: String?, msg: msgFunction) = e(tag, msg.invoke())

inline fun L.e(msg: msgFunction, tr: Throwable)  = e(msg.invoke(), tr)

inline fun L.e(tag: String?, msg: msgFunction, tr: Throwable)  = e(tag, msg.invoke(), tr)

inline fun L.w(msg: msgFunction) = w(msg.invoke())

inline fun L.w(tag: String?, msg: msgFunction) = w(tag, msg.invoke())

inline fun L.w(msg: msgFunction, tr: Throwable)  = w(msg.invoke(), tr)

inline fun L.w(tag: String?, msg: msgFunction, tr: Throwable)  = w(tag, msg.invoke(), tr)

inline fun L.i(msg: msgFunction) = i(msg.invoke())

inline fun L.i(tag: String?, msg: msgFunction) = i(tag, msg.invoke())

inline fun L.i(msg: msgFunction, tr: Throwable)  = i(msg.invoke(), tr)

inline fun L.i(tag: String?, msg: msgFunction, tr: Throwable)  = i(tag, msg.invoke(), tr)

inline fun L.d(msg: msgFunction) = d(msg.invoke())

inline fun L.d(tag: String?, msg: msgFunction) = d(tag, msg.invoke())

inline fun L.d(msg: msgFunction, tr: Throwable)  = d(msg.invoke(), tr)

inline fun L.d(tag: String?, msg: msgFunction, tr: Throwable)  = d(tag, msg.invoke(), tr)