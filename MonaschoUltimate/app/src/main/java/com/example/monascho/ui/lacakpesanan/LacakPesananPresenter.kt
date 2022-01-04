package com.example.monascho.ui.lacakpesanan

import android.util.Log
import com.example.monascho.model.*
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LacakPesananPresenter {
    var view: LacakPesananView

    constructor(view: LacakPesananView) {
        this.view = view
    }

    fun getResponse(idTransaki:String) {
        val api = InitRetrofit().getInitInstance()
        api.getLacakPesanan(idTransaki).enqueue(object : Callback<ResponseLacakPesanan> {
            override fun onResponse(call: Call<ResponseLacakPesanan>, response: Response<ResponseLacakPesanan>) {
                Log.d("lacak", response.toString())
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse as ArrayList<PayloadLacakPesanan>?)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseLacakPesanan>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}