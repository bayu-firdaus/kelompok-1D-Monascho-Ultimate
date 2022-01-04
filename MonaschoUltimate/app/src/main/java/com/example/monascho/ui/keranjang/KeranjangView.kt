package com.example.monascho.ui.keranjang

import com.example.monascho.model.PayloadKeranjang

interface KeranjangView {
    fun onSuccess(payloadKeranjang: ArrayList<PayloadKeranjang>?, total: String)
    fun onErrorResponse()
}