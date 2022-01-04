package com.example.monascho.ui.profil

import com.example.monascho.model.PayloadProfil

interface ProfilView {
    fun onSuccess(payloadProfil: PayloadProfil)
    fun onErrorResponse()
}