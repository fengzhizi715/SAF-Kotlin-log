package com.safframework.log.printer.file.clean

import java.io.File

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.clean.CleanStrategy
 * @author: Tony Shen
 * @date: 2019-10-02 18:51
 * @since: V2.1 文件清空策略
 */
interface CleanStrategy {

    /**
     * Whether we should clean a specified log file.
     *
     * @param file the log file
     * @return true is we should clean the log file
     */
    fun shouldClean(file: File): Boolean
}