package com.safframework.log.printer

import com.alibaba.fastjson.JSON
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.printer.coroutines.ioScope
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.printer.file.FileWriter
import com.safframework.log.printer.file.bean.LogItem
import com.safframework.log.printer.file.clean.CleanStrategy
import com.safframework.log.printer.file.clean.NeverCleanStrategy
import com.safframework.log.printer.file.name.DateFileNameGenerator
import com.safframework.log.printer.file.name.FileNameGenerator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.io.File


/**
 *
 * @FileName:
 *          com.safframework.log.printer.FilePrinter
 * @author: Tony Shen
 * @date: 2019-08-31 10:58
 * @since: V2.0 打印到文件的Printer，默认的 formatter 使用 SimpleFormatter
 */
class FilePrinter(fileBuilder: FileBuilder,override val formatter: Formatter):Printer{

    private val channel = Channel<LogItem>()
    private val folderPath:String
    private val fileNameGenerator: FileNameGenerator
    private val cleanStrategy: CleanStrategy
    private val writer: FileWriter

    init {
        ioScope().launch {
            channel.consumeEach {

                doWrite(it)
            }
        }

        folderPath        = fileBuilder.folderPath?: "/sdcard/logs/"
        fileNameGenerator = fileBuilder.fileNameGenerator?: DateFileNameGenerator()
        cleanStrategy     = fileBuilder.cleanStrategy?: NeverCleanStrategy()

        writer            = FileWriter(folderPath)
    }

    override fun printLog(logLevel: LogLevel, tag: String, msg: String) {

        ioScope().launch {
            channel.send(LogItem(System.currentTimeMillis(), logLevel, tag, msg))
        }
    }

    private fun doWrite(logItem:LogItem) {

        var lastFileName = writer.lastFileName

        if (lastFileName == null || fileNameGenerator.isFileNameChangeable()) {

            val newFileName = fileNameGenerator.generateFileName(logItem.level.value, logItem.tag ,System.currentTimeMillis())

            require(newFileName.trim { it <= ' ' }.isNotEmpty()) { "File name should not be empty." }

            if (newFileName != lastFileName) {
                if (writer.isOpened) {
                    writer.close()
                }

                cleanLogFilesIfNecessary()

                if (!writer.open(newFileName)) {
                    return
                }
            }
        }

        writer.appendLog(JSON.toJSONString(logItem))
    }

    /**
     * 判断是否需要删除日志文件
     */
    private fun cleanLogFilesIfNecessary() {

        File(folderPath).listFiles()?.map {

            if (cleanStrategy.shouldClean(it)) {
                it.delete()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilePrinter

        if (formatter != other.formatter) return false
        if (channel != other.channel) return false
        if (folderPath != other.folderPath) return false
        if (fileNameGenerator != other.fileNameGenerator) return false
        if (cleanStrategy != other.cleanStrategy) return false
        if (writer != other.writer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = formatter.hashCode()
        result = 31 * result + channel.hashCode()
        result = 31 * result + folderPath.hashCode()
        result = 31 * result + fileNameGenerator.hashCode()
        result = 31 * result + cleanStrategy.hashCode()
        result = 31 * result + writer.hashCode()
        return result
    }
}