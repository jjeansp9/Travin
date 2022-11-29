package com.jspstudio.travin.model

data class AccountFriendResponseItem(
    var responseProfile: Int,
    var responseName: String?,
    var allow: Int?,
    var deny: Int?
        )