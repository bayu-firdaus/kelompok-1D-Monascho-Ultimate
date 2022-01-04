package com.example.monascho.model

data class ResponseRiwayatTransaksiDetail (
        var status:Boolean? = null,
        var message:String? = "",
        var subtotal:String = "",
        var ongkir:String = "",
        var total:String = "",
        var payload: ArrayList<PayloadRiwayatTransaksiDetail>? = null
)