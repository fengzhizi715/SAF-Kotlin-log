package com.safframework.log.printer.file.name


/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.LevelFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-10-02 13:54
 * @version: V1.0 <描述当前版本功能>
 */
class LevelFileNameGenerator : FileNameGenerator {

    override fun isFileNameChangeable() = true

    /**
     * Generate a file name which represent a specific log level.
     */
    override fun generateFileName(logLevel: Int, timestamp: Long): String {

        return when(logLevel) {

            0 -> "error"

            1  -> "warn"

            2  -> "info"

            3 -> "debug"

            else -> "info"
        }
    }
}