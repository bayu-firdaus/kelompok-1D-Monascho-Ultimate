package com.example.monascho.ui.riwayattrannsaksidetail

import android.util.Log
import com.example.monascho.model.ResponseRiwayatTransaksiDetail
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatDetailPresenter {
    var view: RiwayatDetailView

    constructor(view: RiwayatDetailView) {
        this.view = view
    }

    fun getResponse(idTransaksi:String) {
        val api = InitRetrofit().getInitInstance()
        api.getRiwayatTransaksiDetail(idTransaksi).enqueue(object : Callback<ResponseRiwayatTransaksiDetail> {
            override fun onResponse(
                    call: Call<ResponseRiwayatTransaksiDetail>,
                    response: Response<ResponseRiwayatTransaksiDetail>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                          view.onSuccess(jsonresponse, response.body()!!.ongkir, response.body()!!.subtotal, response.body()!!.total)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseRiwayatTransaksiDetail>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}