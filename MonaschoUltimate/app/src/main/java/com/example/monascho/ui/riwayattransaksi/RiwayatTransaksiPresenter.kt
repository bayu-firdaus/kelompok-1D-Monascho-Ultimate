package com.example.monascho.ui.riwayattransaksi

import android.util.Log
import com.example.monascho.model.*
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatTransaksiPresenter {
    var view: RiwayatTransaksiView

    constructor(view: RiwayatTransaksiView) {
        this.view = view
    }

    fun getResponse(idKonsumen:String) {
        val api = InitRetrofit().getInitInstance()
        api.getRiwayatTransaksi(idKonsumen).enqueue(object : Callback<ResponseRiwayatTransaksi> {
            override fun onResponse(call: Call<ResponseRiwayatTransaksi>, response: Response<ResponseRiwayatTransaksi>) {
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

            override fun onFailure(call: Call<ResponseRiwayatTransaksi>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}