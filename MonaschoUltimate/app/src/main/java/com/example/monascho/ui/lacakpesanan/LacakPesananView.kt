package com.example.monascho.ui.lacakpesanan

import com.example.monascho.model.PayloadLacakPesanan

interface LacakPesananView {
    fun onSuccess(payloadLacakPesanan: ArrayList<PayloadLacakPesanan>?)
    fun onErrorResponse()
}