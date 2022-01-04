package com.example.monascho.model

data class ResponseRiwayatTransaksi (
        var status:Boolean? = null,
        var message:String? = "",
        var payload: ArrayList<PayloadRiwayatTransaksi>? = null
)