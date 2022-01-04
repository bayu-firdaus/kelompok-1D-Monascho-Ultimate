package com.example.monascho.model

data class ResponseDetailInformasi(
    val message: String,
    val payload: PayloadDetailInformasi,
    val status: Boolean
)