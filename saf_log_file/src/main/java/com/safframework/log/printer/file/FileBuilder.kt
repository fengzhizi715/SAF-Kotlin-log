package com.safframework.log.printer.file

import com.safframework.log.formatter.Formatter
import com.safframework.log.formatter.SimpleFormatter
import com.safframework.log.printer.FilePrinter
import com.safframework.log.printer.file.clean.CleanStrategy
import com.safframework.log.printer.file.name.FileNameGenerator


/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.FileBuilder
 * @author: Tony Shen
 * @date: 2019-08-31 21:53
 * @since: V2.0 生成文件的 Builder
 */
class FileBuilder{

    var folderPath: String? = null

    var fileNameGenerator: FileNameGenerator? = null // The file name generator for log file.

    var formatter:Formatter? = null

    var cleanStrategy:CleanStrategy? = null

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

    fun cleanStrategy(cleanStrategy:CleanStrategy): FileBuilder {
        this.cleanStrategy = cleanStrategy
        return this
    }

    fun build() = FilePrinter(this,this.formatter?: SimpleFormatter)
}