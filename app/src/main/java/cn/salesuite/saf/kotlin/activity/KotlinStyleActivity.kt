package cn.salesuite.saf.kotlin.activity

import android.app.Activity
import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.i

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.KotlinStyleActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:33
 * @version: V1.0 <描述当前版本功能>
 */
class KotlinStyleActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        L.i {

            val message = "kotlin"
            "hi $message"
        }

        L.i("customerTag") {

            val message = "tony"
            "hi $message"
        }
    }
}