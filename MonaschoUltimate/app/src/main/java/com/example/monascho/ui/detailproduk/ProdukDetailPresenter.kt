package com.example.monascho.ui.detailproduk

import android.util.Log
import com.example.monascho.model.*
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukDetailPresenter {
    var view: ProdukDetailView

    constructor(view: ProdukDetailView) {
        this.view = view
    }

    fun getResponse(id:String) {
        val api = InitRetrofit().getInitInstance()
        api.getProdukDetail(id).enqueue(object : Callback<ResponseProdukDetail> {
            override fun onResponse(call: Call<ResponseProdukDetail>, response: Response<ResponseProdukDetail>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}