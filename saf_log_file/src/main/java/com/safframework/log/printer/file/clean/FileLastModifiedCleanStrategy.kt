package com.safframework.log.printer.file.clean

import java.io.File

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.clean.FileLastModifiedCleanStrategy
 * @author: Tony Shen
 * @date: 2019-10-02 18:53
 * @since: V2.1 根据设置文件可以保存的最大时间，来实现文件的清除策略
 */
class FileLastModifiedCleanStrategy(val maxTimeMillis: Long) : CleanStrategy {

    override fun shouldClean(file: File): Boolean {

        val currentTimeMillis = System.currentTimeMillis()
        val lastModified = file.lastModified()
        return currentTimeMillis - lastModified > maxTimeMillis
    }
}