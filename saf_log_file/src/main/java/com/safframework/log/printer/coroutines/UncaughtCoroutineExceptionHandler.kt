package com.safframework.log.printer.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.log.printer.coroutines.UncaughtCoroutineExceptionHandler
 * @author: Tony Shen
 * @date: 2019-11-04 17:44
 * @version: V1.0 <描述当前版本功能>
 */
class UncaughtCoroutineExceptionHandler(val errorHandler: CoroutineErrorListener?=null)  :
        CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, throwable: Throwable) {
        throwable.printStackTrace()

        errorHandler?.let {
            it.onError(throwable)
        }
    }
}