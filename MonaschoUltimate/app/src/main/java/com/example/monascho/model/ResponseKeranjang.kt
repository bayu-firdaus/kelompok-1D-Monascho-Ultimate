package com.example.monascho.model


data class ResponseKeranjang (
        var status:Boolean? = null,
        var message:String? = "",
        var total:String? = "",
        var payload: ArrayList<PayloadKeranjang>? = null
)