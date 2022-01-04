package com.example.monascho.model

data class PayloadKeranjang(
        var id_keranjang:String = "",
        var id_produk:String = "",
        var nama_produk:String = "",
        var harga:String = "",
        var foto:String = "",
        var kuantitas_item:String = ""
)