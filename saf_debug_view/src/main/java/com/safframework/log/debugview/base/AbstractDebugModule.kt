package com.safframework.log.debugview.base

import android.view.View
import android.view.ViewGroup

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.base.AbstractDebugModule
 * @author: Tony Shen
 * @date: 2019-11-30 13:58
 * @version: V1.0 <描述当前版本功能>
 */
abstract class AbstractDebugModule<T>(protected val dataModule: IDataModule<T>?, private val viewModule: IViewModule<T>?) {

    init {
        this.dataModule?.bind(this.viewModule)
    }

    fun start() {
        dataModule?.bind(viewModule)
        dataModule?.start()
    }

    fun stop() {
        dataModule?.unBind(viewModule)
        dataModule?.stop()
    }

    fun reset() {
        dataModule?.onReset()
    }

    fun createView(root: ViewGroup): View? {
        return viewModule?.onCreateView(root)
    }

}