package com.example.monascho.model

data class PayloadRiwayatTransaksi(
    var id_transaksi:String = "",
    var tgl_transaksi:String = "",
    var id_konsumen:String = "",
    var jumlah_transaksi:String = "",
    var status:String = "",
    var total:String = ""
)