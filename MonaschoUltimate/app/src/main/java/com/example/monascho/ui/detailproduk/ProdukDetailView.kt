package com.example.monascho.ui.detailproduk

import com.example.monascho.model.PayloadProdukDetail

interface ProdukDetailView {
    fun onSuccess(payloadProdukDetail: PayloadProdukDetail)
    fun onErrorResponse()
}