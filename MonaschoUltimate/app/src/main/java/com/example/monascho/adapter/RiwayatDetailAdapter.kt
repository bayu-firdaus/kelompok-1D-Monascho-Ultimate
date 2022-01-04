package com.example.monascho.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.R
import com.example.monascho.model.PayloadRiwayatTransaksiDetail
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.retur.ReturActivity
import com.squareup.picasso.Picasso

class RiwayatDetailAdapter : RecyclerView.Adapter<RiwayatDetailAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadRiwayatTransaksiDetail>? = null

    constructor(context: Context, data: ArrayList<PayloadRiwayatTransaksiDetail>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_riwayat_detail, p0, false)
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
        var tvKuantitas:TextView = itemView.findViewById(R.id.tvKuantitas)
        var tvTotal:TextView = itemView.findViewById(R.id.tvTotal)
        var img:ImageView = itemView.findViewById(R.id.img)
        var btnRetur:Button = itemView.findViewById(R.id.btnRetur)
        var tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvNamaProduk.text = dataList?.get(position)!!.nama_produk!!
        holder.tvKuantitas.text = "Jumlah ${dataList?.get(position)!!.kuantitas!!}"
//        holder.tvTotal.text = ("Total = ${FormatRp.parsingRupiah((dataList?.get(position)!!.total!!.toInt()))}")
        holder.tvTotal.text = "Total = "+(dataList?.get(position)!!.total!!)


        if (dataList?.get(position)!!.status_transaksi == "5"){
            holder.btnRetur.visibility = View.VISIBLE
        }else{
            holder.btnRetur.visibility = View.GONE
        }

        holder.btnRetur.setOnClickListener {
            var intent = Intent(it.context, ReturActivity::class.java)
            intent.putExtra("id_item", dataList?.get(position)!!.id_item!!)
            intent.putExtra("kuantitas", dataList?.get(position)!!.kuantitas!!)
            it.context.startActivity(intent)
        }

        val api = InitRetrofit().getFolderImgProduk()
        Picasso.with(c).load("$api${dataList?.get(position)!!.foto!!}").resize(200, 250).into(holder.img)

        when {
            dataList?.get(position)!!.status!! == "1" -> {
                holder.btnRetur.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#ff4d6b"))
                holder.tvStatus.text = "Menunggu Konfirmasi retur dari penjual"
            }
            dataList?.get(position)!!.status!! == "2" -> {
                holder.btnRetur.visibility = View.GONE
                holder.tvStatus.visibility = View.VISIBLE
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.tvStatus.text = "Permintaan retur dikonfirmasi"
            }dataList?.get(position)!!.status!! == "3" -> {
                holder.tvStatus.visibility = View.GONE
                holder.btnRetur.visibility = View.GONE
            }else -> {
                holder.tvStatus.visibility = View.GONE
            }
        }
    }
}
