package cn.salesuite.saf.kotlin.activity

import android.app.Activity
import android.os.Bundle
import cn.salesuite.saf.kotlin.domain.User
import cn.salesuite.saf.kotlin.handler.UserHandler
import com.safframework.log.L

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.CustomerHandlerActivity
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