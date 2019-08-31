package com.safframework.log.printer.file

import com.safframework.log.printer.FilePrinter


/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.FileBuilder
 * @author: Tony Shen
 * @date: 2019-08-31 21:53
 * @version: V1.0 <描述当前版本功能>
 */
class FileBuilder(var folderPath: String) {

    /**
     * The file name generator for log file.
     */
    var fileNameGenerator: FileNameGenerator? = null

    fun fileNameGenerator(fileNameGenerator: FileNameGenerator): FileBuilder {
        this.fileNameGenerator = fileNameGenerator
        return this
    }

    fun build(): FilePrinter {

        return FilePrinter(this)
    }
}