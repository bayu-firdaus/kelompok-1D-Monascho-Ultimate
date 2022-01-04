package com.example.monascho.model

data class ResponseInformasi(
    val message: String,
    val payload: List<PayloadInformasi>,
    val status: Boolean
)