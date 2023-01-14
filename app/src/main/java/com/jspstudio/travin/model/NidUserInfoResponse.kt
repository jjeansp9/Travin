package com.jspstudio.travin.model

data class NidUserInfoResponse(
    var resultCode:String,
    var message:String,
    var response: NidUser
    )


data class NidUser(
    var id:String,
    var email:String
    )
