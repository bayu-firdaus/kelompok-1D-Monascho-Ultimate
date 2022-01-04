package com.example.monascho.ui.detailinformasi

import com.example.monascho.model.PayloadDetailInformasi

interface InformasiDetailView {
    fun onSuccess(payloadDetailInformasi: PayloadDetailInformasi)
    fun onErrorResponse()
}