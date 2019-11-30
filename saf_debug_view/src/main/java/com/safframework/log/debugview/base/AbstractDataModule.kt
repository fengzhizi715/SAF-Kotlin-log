package com.safframework.log.debugview.base

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.base.AbstractDataModule
 * @author: Tony Shen
 * @date: 2019-11-30 14:00
 * @version: V1.0 <描述当前版本功能>
 */
abstract class AbstractDataModule<T> : IDataModule<T> {

    private var viewModule: IViewModule<T>? = null

    protected abstract val data: T

    override fun bind(viewModule: IViewModule<T>?) {
        this.viewModule = viewModule
    }

    override fun unBind(viewModule: IViewModule<T>?) {
        this.viewModule = null
    }

    override fun notifyUpdate() {
        viewModule?.let {
            it.onUpdateView(data)
        }
    }

    override fun setVisibility(visibility: Int) {
        viewModule?.let {
            it.onSetVisibility(visibility)
        }
    }
}