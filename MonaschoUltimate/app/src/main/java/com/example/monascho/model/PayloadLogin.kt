package com.example.monascho.model

data class PayloadLogin(
    val id_konsumen: String,
    val nama: String,
    val no_telp: String,
    val password: String,
    val tgl_create: String,
    val token: String
)