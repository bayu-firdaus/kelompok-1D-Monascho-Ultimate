package com.example.monascho.model

data class ResponseLacakPesanan(
        val message: String,
        val payload: List<PayloadLacakPesanan>,
        val status: Boolean
)