package com.example.monascho.ui.detailinformasi

import android.util.Log
import com.example.monascho.model.*
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformasiDetailPresenter {
    var view: InformasiDetailView

    constructor(view: InformasiDetailView) {
        this.view = view
    }

    fun getResponse(id:String) {
        val api = InitRetrofit().getInitInstance()
        api.getInformasiDetail(id).enqueue(object : Callback<ResponseDetailInformasi> {
            override fun onResponse(call: Call<ResponseDetailInformasi>, response: Response<ResponseDetailInformasi>) {
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

            override fun onFailure(call: Call<ResponseDetailInformasi>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}