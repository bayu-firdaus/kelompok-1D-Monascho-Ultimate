package com.example.monascho.model

data class ResponseSignUp (
    var status:Boolean,
    var message: String = "",
    var password : String = ""
)