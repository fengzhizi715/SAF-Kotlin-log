package com.safframework.log.debugview.base

import android.view.View
import android.view.ViewGroup

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.base.AbstractDebugModule
 * @author: Tony Shen
 * @date: 2019-11-30 13:58
 * @version: V2.2 <描述当前版本功能>
 */
abstract class AbstractDebugModule<T>(protected val dataModule: IDataModule<T>?, private val viewModule: IViewModule<T>?) {

    init {
        this.dataModule?.bind(this.viewModule)
    }

    fun start() {
        dataModule?.apply {
            bind(viewModule)
            start()
        }
    }

    fun stop() {
        dataModule?.apply {
            unBind(viewModule)
            stop()
        }
    }

    fun reset() {
        dataModule?.onReset()
    }

    fun createView(root: ViewGroup): View? {
        return viewModule?.onCreateView(root)
    }

}