package com.safframework.log.extension

import com.safframework.log.LoggerPrinter
import org.json.JSONArray

/**
 *
 * @FileName:
 *          com.safframework.log.extension.`JSONArray+Extension`
 * @author: Tony Shen
 * @date: 2019-12-08 20:38
 * @version: V2.4 <描述当前版本功能>
 */

fun JSONArray.formatJSON(): String = this.toString(LoggerPrinter.JSON_INDENT)