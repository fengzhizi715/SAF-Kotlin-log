package com.safframework.log.printer.file.bean

import com.safframework.log.LogLevel

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.bean.LogItem
 * @author: Tony Shen
 * @date: 2019-11-04 17:09
 * @version: V2.0 每次写入文件的内容，记录了当前时间、LogLevel、tag、msg
 */
class LogItem(var timeMillis: Long, var level: LogLevel, var tag: String, var msg: String)