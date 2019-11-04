package com.safframework.log.printer.coroutines

import kotlinx.coroutines.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.coroutines.CoroutineScopes
 * @author: Tony Shen
 * @date: 2019-11-04 17:43
 * @version: V2.2 <描述当前版本功能>
 */
val UI: CoroutineDispatcher = Dispatchers.Main

val IO: CoroutineDispatcher = Dispatchers.IO

val Default: CoroutineDispatcher = Dispatchers.Default

// 运行在主线程，支持异常处理、无返回结果
fun runOnUI(block: suspend CoroutineScope.() -> Unit) = uiScope().launch(block = block)

// 运行在后台线程，支持异常处理、无返回结果
fun runInBackground(block: suspend CoroutineScope.() -> Unit) = ioScope().launch(block = block)

// 运行在主线程，支持异常处理、有返回结果
fun <T> asyncOnUI(block: suspend CoroutineScope.() -> T) = uiScope().async(block = block)

// 运行在后台线程，支持异常处理、有返回结果
fun <T> asyncInBackground(block: suspend CoroutineScope.() -> T) = ioScope().async(block = block)


fun ioScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(IO,errorHandler)

fun uiScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(UI,errorHandler)

fun defaultScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(Default,errorHandler)

fun customScope(dispatcher: CoroutineDispatcher, errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(dispatcher,errorHandler)