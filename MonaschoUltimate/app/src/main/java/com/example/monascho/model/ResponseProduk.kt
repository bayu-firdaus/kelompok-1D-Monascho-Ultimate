package com.example.monascho.model

data class ResponseProduk(
    val message: String,
    val payload: List<PayloadProduk>,
    val status: Boolean
)