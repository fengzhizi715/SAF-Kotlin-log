package cn.salesuite.saf.kotlin.domain

import java.io.Serializable

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.domain.UserEventModel
 * @author: Tony Shen
 * @date: 2018-06-18 21:21
 * @version V1.0 <描述当前版本功能>
 */
data class Event(var id: String,
                 var type: String,
                 var actor: Actor,
                 var repo: Repo,
                 var payload: Payload,
                 var public: Boolean,
                 var created_at: String) : Serializable

data class Actor(var id: String,
                 var login: String,
                 var display_login: String,
                 var gravatar_id: String,
                 var url: String,
                 var avatar_url: String) : Serializable

data class Repo(var id: String,
                var name: String,
                var url: String) : Serializable

data class Payload(var push_id: Long,
                   var size: Int,
                   var distinct_size: Int,
                   var ref: String,
                   var head: String,
                   var before: String,
                   var commits: List<Commit>) : Serializable

data class Commit(var sha: String,
                  var author: Author,
                  var message: String,
                  var distinct: Boolean,
                  var url: String) : Serializable

data class Author(var email:String,
                  var name:String) : Serializable