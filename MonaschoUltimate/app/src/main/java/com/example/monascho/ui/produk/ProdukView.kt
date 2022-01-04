package com.example.monascho.ui.produk

import com.example.monascho.model.PayloadProduk

interface ProdukView {
    fun onSuccess(payloadProduk: ArrayList<PayloadProduk>?)
    fun onErrorResponse()
}