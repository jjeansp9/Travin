package com.jspstudio.travin

data class UserData (
    var id:String,
    var nickname:String,
    var password:String
    )



class UserDatas {
    companion object {
        var id
                : String? = null
        var password
                : String? = null
        var nickname
                : String? = null
        var profileUrl : String? = null
    }
}
