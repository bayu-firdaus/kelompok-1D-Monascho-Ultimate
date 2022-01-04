package com.example.monascho.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.R
import com.example.monascho.model.PayloadKeranjang
import com.example.monascho.model.ResponsePostKeranjang
import com.example.monascho.network.InitRetrofit
import com.example.monascho.utils.FormatRp
import com.example.monascho.utils.TmpData
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeranjangAdapter : RecyclerView.Adapter<KeranjangAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadKeranjang>? = null
    private var jum: Int = 0
    private var idUser = ""
    private var broadcaster: LocalBroadcastManager? = null

    constructor(context: Context, data: ArrayList<PayloadKeranjang>?) {
        this.c = context
        this.dataList = data

        val pref: SharedPreferences = c!!.getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_keranjang, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNamaProduk:TextView = itemView.findViewById(R.id.tvNamaProduk)
        var tvHarga:TextView = itemView.findViewById(R.id.tvHarga)
        var tvItem:TextView = itemView.findViewById(R.id.tvItem)
        var btnTambah:ImageView = itemView.findViewById(R.id.btnTambah)
        var btnKurang:ImageView = itemView.findViewById(R.id.btnKurang)
        var chbContent:CheckBox = itemView.findViewById(R.id.chbContent)
        var tvTotal:TextView = itemView.findViewById(R.id.tvTotal)
        var img:ImageView = itemView.findViewById(R.id.img)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        jum = Integer.valueOf("" + holder.tvItem.text.toString())

        holder.btnTambah.setOnClickListener {
            TmpData.idProduk.clear()
            TmpData.kuantitas.clear()
            jum = Integer.valueOf(holder.tvItem.text.toString())

            try {
                jum += 1
                holder.tvItem.text = jum.toString()
            } catch (e: java.lang.Exception) {
            }
            updateKeranjang(dataList?.get(position)!!.id_keranjang!!, jum.toString())
//            broadCast("2")
//            (c as FragmentKeranjang?)!!.pb1.visibility = View.VISIBLE
        }

        holder.btnKurang.setOnClickListener {
            TmpData.idProduk.clear()
            TmpData.kuantitas.clear()
            jum = Integer.valueOf("" + holder.tvItem.text.toString())
            if (jum == 1) {
            } else if (jum > 1) {
                try {
                    jum -= 1
                    holder.tvItem.text = "" + jum
                } catch (e: Exception) {
                }
            }

            updateKeranjang(dataList?.get(position)!!.id_keranjang!!, jum.toString())
//            broadCast("2")
//            (c as FragmentKeranjang?)!!.pb1.visibility = View.VISIBLE
        }

        holder.chbContent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                TmpData.idKeranjang.add(dataList?.get(position)!!.id_keranjang!!)
                TmpData.idProduk.add(dataList?.get(position)!!.id_produk!!)
                TmpData.kuantitas.add(dataList?.get(position)!!.kuantitas_item!!)
                Log.d("chek", dataList?.get(position)!!.nama_produk!!+" => "+dataList?.get(position)!!.kuantitas_item!!)

            } else {
                TmpData.idKeranjang.remove(dataList?.get(position)!!.id_keranjang!!)
                TmpData.idProduk.remove(dataList?.get(position)!!.id_produk!!)
                TmpData.kuantitas.remove(dataList?.get(position)!!.kuantitas_item!!)
                Log.d("chek", "remover")
            }

        }

        holder.tvNamaProduk.text = dataList?.get(position)!!.nama_produk!!
        holder.tvHarga.text = "${FormatRp.parsingRupiah(dataList?.get(position)!!.harga!!.toInt())}"
        holder.tvItem.text = dataList?.get(position)!!.kuantitas_item!!
        holder.tvTotal.text = ("Total = ${FormatRp.parsingRupiah((dataList?.get(position)!!.harga!!.toInt() * dataList?.get(position)!!.kuantitas_item!!.toInt()))}")

        val api = InitRetrofit().getFolderImgProduk()
        Picasso.with(c).load("$api${dataList?.get(position)!!.foto!!}").resize(200, 250).into(holder.img)
    }

    private fun updateKeranjang (idKeranjang:String, kuantitasItem:String) {
        val api = InitRetrofit().getInitInstance()
        api.updateKeranjang(idKeranjang,kuantitasItem).enqueue(object :
                Callback<ResponsePostKeranjang> {
            override fun onResponse(
                    call: Call<ResponsePostKeranjang>,
                    response: Response<ResponsePostKeranjang>
            ) {
                if (response.isSuccessful) {
//                    (c as FragmentKeranjang?)!!.keranjangPresenter.getResponse(idUser)
                    broadCast("1")
                } else {
//                    binding!!.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponsePostKeranjang>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }

    private fun broadCast (jenis:String) {
        broadcaster = LocalBroadcastManager.getInstance(c!!)
        val intent = Intent("MyData")
        intent.putExtra("refresh", jenis)
        broadcaster!!.sendBroadcast(intent)
    }
}
