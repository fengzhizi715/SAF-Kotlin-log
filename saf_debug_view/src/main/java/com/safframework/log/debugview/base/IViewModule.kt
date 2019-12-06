package com.safframework.log.debugview.base

import android.view.View
import android.view.ViewGroup

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.base.IViewModule
 * @author: Tony Shen
 * @date: 2019-11-30 13:37
 * @version: V2.2 <描述当前版本功能>
 */
interface IViewModule<T> {

    fun onCreateView(root: ViewGroup): View?

    fun onUpdateView(data: T)

    fun onSetVisibility(visibility: Int)
}