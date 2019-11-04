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
val IO: CoroutineDispatcher = Dispatchers.IO

fun ioScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(IO,errorHandler)