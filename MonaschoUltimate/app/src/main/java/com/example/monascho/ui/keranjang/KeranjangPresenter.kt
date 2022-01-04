package com.example.monascho.ui.keranjang

import android.util.Log
import com.example.monascho.model.ResponseKeranjang
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeranjangPresenter {
    var view: KeranjangView

    constructor(view: KeranjangView) {
        this.view = view
    }

    fun getResponse(id_konsumen:String) {
        val api = InitRetrofit().getInitInstance()
        api.getKeranjang(id_konsumen).enqueue(object : Callback<ResponseKeranjang> {
            override fun onResponse(
                    call: Call<ResponseKeranjang>,
                    response: Response<ResponseKeranjang>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse, response.body()!!.total.toString())
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseKeranjang>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}