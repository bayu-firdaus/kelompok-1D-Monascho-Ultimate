package com.example.monascho.model

data class ResponseLogin(
    val message: String,
    val payload: PayloadLogin,
    val status: Boolean
)