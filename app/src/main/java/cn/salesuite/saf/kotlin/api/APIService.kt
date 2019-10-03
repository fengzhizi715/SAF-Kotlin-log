package cn.salesuite.saf.kotlin.api

import cn.salesuite.saf.kotlin.domain.Event
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.api.APIService
 * @author: Tony Shen
 * @date: 2018-06-18 19:28
 * @version V1.0 <描述当前版本功能>
 */
interface APIService {

    companion object {

        const val API_BASE_SERVER_URL = "https://api.github.com/"
    }

    @GET("users/{username}/events/public")
    fun publicEvent(@Path("username")userName:String): Maybe<List<Event>>
}