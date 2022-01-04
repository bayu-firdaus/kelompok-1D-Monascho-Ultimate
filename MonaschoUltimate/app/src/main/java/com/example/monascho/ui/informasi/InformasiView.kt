package com.example.monascho.ui.informasi

import com.example.monascho.model.PayloadInformasi

interface InformasiView {
    fun onSuccess(payloadInformasi: ArrayList<PayloadInformasi>?)
    fun onErrorResponse()
}