package com.example.monascho.model

data class PayloadLacakPesanan(
    val id_transaksi: String,
    val jam: String,
    val jenis: String,
    val nomor: String,
    val pesan: String,
    val tgl: String
)