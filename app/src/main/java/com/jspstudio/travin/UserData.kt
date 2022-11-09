package com.jspstudio.travin

data class UserData (
    var id:String,
    var nickname:String,
    var password:String
    )

object UserDatas {
    var id
            : String? = null
    var password
            : String? = null
    var nickname
            : String? = null
}