package com.anytask.models.dto.user

data class Login(
    var userName: String,
    var password:String,
    var pawnNo:Int,
    var imgURL: String
)
