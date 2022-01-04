package com.example.monascho.ui.produk

import android.util.Log
import com.example.monascho.model.PayloadProduk
import com.example.monascho.model.ResponseProduk
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukPresenter {
    var view: ProdukView

    constructor(view: ProdukView) {
        this.view = view
    }

    fun getResponse(nama:String) {
        val api = InitRetrofit().getInitInstance()
        api.getProduk(nama).enqueue(object : Callback<ResponseProduk> {
            override fun onResponse(call: Call<ResponseProduk>, response: Response<ResponseProduk>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse as ArrayList<PayloadProduk>)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseProduk>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}