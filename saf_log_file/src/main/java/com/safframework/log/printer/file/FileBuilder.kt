package com.safframework.log.printer.file

import com.safframework.log.formatter.Formatter
import com.safframework.log.formatter.SimpleFormatter
import com.safframework.log.printer.FilePrinter


/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.FileBuilder
 * @author: Tony Shen
 * @date: 2019-08-31 21:53
 * @version: V1.0 <描述当前版本功能>
 */
class FileBuilder{

    var folderPath: String? = null

    var fileNameGenerator: FileNameGenerator? = null // The file name generator for log file.

    var formatter:Formatter? = null

    fun folderPath(folderPath: String): FileBuilder {
        this.folderPath = folderPath
        return this
    }

    fun fileNameGenerator(fileNameGenerator: FileNameGenerator): FileBuilder {
        this.fileNameGenerator = fileNameGenerator
        return this
    }

    fun formatter(formatter:Formatter): FileBuilder {
        this.formatter = formatter
        return this
    }

    fun build(): FilePrinter {

        return FilePrinter(this,this.formatter?: SimpleFormatter())
    }
}