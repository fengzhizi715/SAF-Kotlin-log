package cn.salesuite.saf.kotlin

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import com.safframework.log.L

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.CustomerHandlerActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:26
 * @version: V1.0 <描述当前版本功能>
 */
class CustomerHandlerActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val u = User()
        u.userName = "tony"
        u.password = "123456"

        L.addCustomerHandler(UserHandler())
        L.json(u)
    }
}