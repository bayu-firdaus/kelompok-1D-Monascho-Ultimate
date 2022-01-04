package com.example.monascho.ui.riwayattrannsaksidetail

import com.example.monascho.model.PayloadRiwayatTransaksiDetail


interface RiwayatDetailView {
    fun onSuccess(payloadRiwayatTransaksiDetail: ArrayList<PayloadRiwayatTransaksiDetail>?, ongkir:String, subTotal:String, total:String)
    fun onErrorResponse()
}