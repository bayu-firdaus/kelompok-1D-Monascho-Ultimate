package com.example.monascho.ui.profil

import android.util.Log
import com.example.monascho.model.*
import com.example.monascho.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilPresenter {
    var view: ProfilView

    constructor(view: ProfilView) {
        this.view = view
    }

    fun getResponse(idKonsumen:String) {
        val api = InitRetrofit().getInitInstance()
        api.getProfil(idKonsumen).enqueue(object : Callback<ResponseProfil> {
            override fun onResponse(call: Call<ResponseProfil>, response: Response<ResponseProfil>) {
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

            override fun onFailure(call: Call<ResponseProfil>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}