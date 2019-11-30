package com.safframework.log.debugview.base

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.base.IDataModule
 * @author: Tony Shen
 * @date: 2019-11-30 13:58
 * @version: V1.0 <描述当前版本功能>
 */
interface IDataModule<T> {

    fun start()

    fun stop()

    fun onReset()

    fun notifyUpdate()

    fun bind(viewModule: IViewModule<T>?)

    fun unBind(viewModule: IViewModule<T>?)

    fun setVisibility(visibility: Int)
}