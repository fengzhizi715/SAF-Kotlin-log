package com.safframework.log.printer.file.clean

import java.io.File

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.clean.FileLastModifiedCleanStrategy
 * @author: Tony Shen
 * @date: 2019-10-02 18:53
 * @version: V2.1 <描述当前版本功能>
 */
class FileLastModifiedCleanStrategy(private val maxTimeMillis: Long) : CleanStrategy {

    override fun shouldClean(file: File): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val lastModified = file.lastModified()
        return currentTimeMillis - lastModified > maxTimeMillis
    }
}