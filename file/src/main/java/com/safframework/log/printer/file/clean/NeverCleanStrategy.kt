package com.safframework.log.printer.file.clean

import java.io.File

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.clean.NeverCleanStrategy
 * @author: Tony Shen
 * @date: 2019-10-02 18:52
 * @since: V2.1 文件永不清空的策略
 */
class NeverCleanStrategy : CleanStrategy {

    override fun shouldClean(file: File) = false
}