package com.example.monascho.model

data class ResponsePostKeranjang (
    var status:Boolean,
    var message: String = "",
    var payload : String = ""
)