package com.example.textmeapp.DataClass

data class Message(
    var message:String = "",
    var sender_uid:String? = "",
    var time:Long = 0
)