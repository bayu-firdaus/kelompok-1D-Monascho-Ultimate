package com.example.monascho.ui.riwayattransaksi

import com.example.monascho.model.PayloadRiwayatTransaksi

interface RiwayatTransaksiView {
    fun onSuccess(payloadRiwayatTransaksi: ArrayList<PayloadRiwayatTransaksi>?)
    fun onErrorResponse()
}