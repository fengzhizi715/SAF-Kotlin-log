package com.safframework.log.printer.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.log.printer.coroutines.SafeCoroutineScope
 * @author: Tony Shen
 * @date: 2019-11-04 17:43
 * @version: V2.2 <描述当前版本功能>
 */
class SafeCoroutineScope(context: CoroutineContext, errorHandler: CoroutineErrorListener?=null) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext = SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}