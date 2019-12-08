package com.safframework.log.printer.coroutines

/**
 *
 * @FileName:
 *          com.safframework.log.printer.coroutines.CoroutineErrorListener
 * @author: Tony Shen
 * @date: 2019-11-04 17:44
 * @version: V2.2 <描述当前版本功能>
 */
interface CoroutineErrorListener {

    fun onError(throwable: Throwable)
}