package com.safframework.log.debugview.domain

import android.app.ActivityManager
import android.os.Debug

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.domain.MemInfo
 * @author: Tony Shen
 * @date: 2019-11-30 13:20
 * @version: V1.0 <描述当前版本功能>
 */
class MemInfo(val systemMemInfo: ActivityManager.MemoryInfo,
              val processMemInfo: Debug.MemoryInfo)