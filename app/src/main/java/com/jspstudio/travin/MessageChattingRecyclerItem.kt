package com.jspstudio.travin

data class MessageChattingRecyclerItem(

    var nickname:String?,
    var message: String?,
    var time: String?,
)

data class MessageFriendRecyclerItem(
    var profile: Any,
    var name: String?
)