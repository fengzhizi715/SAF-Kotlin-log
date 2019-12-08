package cn.salesuite.saf.kotlin.activity

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import cn.salesuite.saf.kotlin.domain.User
import com.safframework.log.L

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.BasicActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:11
 * @version: V1.0 <描述当前版本功能>
 */
class BasicActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj")

        val u = User()
        u.userName = "tony"
        u.password = "123456"
        L.json(u)

        val map = HashMap<String, User>()
        map["tony"] = u
        map["tt"] = u
        L.json(map)

        val map2 = HashMap<String, String>()
        map2["tony"] = "shen"
        map2["tt"] = "ziyu"
        L.json(map2)

        val map3 = HashMap<String, Boolean>()
        map3["tony"] = true
        map3["tt"] = false
        L.json(map3)

        val list = listOf<User>(u,u)
        L.json(list)

        val ids = listOf<String>("123","456")
        L.json(ids)

        val idd = listOf<Double>(123.toDouble(),456.toDouble())
        L.json(idd)

        val uri = Uri.parse ("http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic")
        L.json(uri)
    }

}