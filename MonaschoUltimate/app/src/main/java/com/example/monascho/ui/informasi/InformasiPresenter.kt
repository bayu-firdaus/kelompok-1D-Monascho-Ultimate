package com.example.monascho.ui.informasi

import android.util.Log
import com.example.monascho.model.PayloadInformasi
import com.example.monascho.model.ResponseInformasi
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformasiPresenter {
    var view: InformasiView

    constructor(view: InformasiView) {
        this.view = view
    }

    fun getResponse() {
        val api = InitRetrofit().getInitInstance()
        api.getInformasi().enqueue(object : Callback<ResponseInformasi> {
            override fun onResponse(call: Call<ResponseInformasi>, response: Response<ResponseInformasi>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse as ArrayList<PayloadInformasi>)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseInformasi>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}