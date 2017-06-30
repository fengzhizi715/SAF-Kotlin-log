package com.safframework.log

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Any.e(text: String)  = L.e(text)

fun Any.w(text: String)  = L.w(text)

fun Any.i(text: String)  = L.d(text)

fun Any.d(text: String)  = L.i(text)

fun Any.json()  = L.json(this)

fun String.e() = L.e(this)

fun String.w() = L.w(this)

fun String.i() = L.i(this)

fun String.d() = L.d(this)

fun String.json() = L.json(this)
