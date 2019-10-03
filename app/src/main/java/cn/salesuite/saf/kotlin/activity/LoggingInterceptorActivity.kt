package cn.salesuite.saf.kotlin.activity

import android.app.Activity
import android.os.Bundle
import cn.salesuite.saf.kotlin.http.RetrofitManager
import com.safframework.utils.RxJavaUtils

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.LoggingInterceptorActivity
 * @author: Tony Shen
 * @date: 2019-10-03 10:38
 * @version: V1.0 <描述当前版本功能>
 */
class LoggingInterceptorActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    private fun initData() {

        RetrofitManager.get()
                .apiService()
                .publicEvent("fengzhizi715")
                .compose(RxJavaUtils.maybeToMain())
                .subscribe()
    }
}