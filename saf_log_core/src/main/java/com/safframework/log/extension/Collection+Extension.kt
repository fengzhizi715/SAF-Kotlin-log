package com.safframework.log.extension

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * @FileName:
 *          com.safframework.log.extension.`Collection+Extension`
 * @author: Tony Shen
 * @date: 2019-12-08 20:39
 * @version: V2.4 <描述当前版本功能>
 */


/**
 * 解析 collection ，并存储到 JSONArray
 */
fun Collection<*>.parseToJSONArray(jsonArray: JSONArray): JSONArray {

    this.map {

        try {
            val objStr = JSON.toJSONString(it)
            val jsonObject = JSONObject(objStr)
            jsonArray.put(jsonObject)
        } catch (e: JSONException) {
            L.e("Invalid Json")
        }
    }

    return jsonArray
}