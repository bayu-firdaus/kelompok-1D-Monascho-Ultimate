package com.example.monascho.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.R
import com.example.monascho.model.PayloadProduk
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.detailproduk.DetailProdukActivity
import com.squareup.picasso.Picasso

class ProdukAdapter : RecyclerView.Adapter<ProdukAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadProduk>? = null

    constructor(context: Context, data: ArrayList<PayloadProduk>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_produk, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama:TextView = itemView.findViewById(R.id.tvNama)
        var tvDeskripsi:TextView = itemView.findViewById(R.id.tvDeskripsi)
        var tvHarga:TextView = itemView.findViewById(R.id.tvHarga)
        var tvStok:TextView = itemView.findViewById(R.id.tvStok)
        var tvDiskon:TextView = itemView.findViewById(R.id.tvDiskon)
        var img:ImageView = itemView.findViewById(R.id.img)
        var cardView:CardView = itemView.findViewById(R.id.cardBuku)
    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvNama.text = dataList?.get(position)!!.nama!!
        holder.tvDeskripsi.text = dataList?.get(position)!!.deskripsi!!
        holder.tvHarga.text = dataList?.get(position)!!.harga!!
        holder.tvStok.text = "Stok " +dataList?.get(position)!!.stok!!

        if (dataList?.get(position)!!.diskon!!.toInt() > 0) {
            holder.tvDiskon.visibility = View.VISIBLE
        }

        holder.tvDiskon.text = "Diskon " +dataList?.get(position)!!.diskon!! + "%"

        val api = InitRetrofit().getFolderImgProduk()
        Picasso.with(c).load("$api${dataList?.get(position)!!.foto!!}").resize(200, 250).into(holder.img)

        holder.cardView.setOnClickListener {
            var intent = Intent(it.context, DetailProdukActivity::class.java)
            intent.putExtra("id", dataList?.get(position)!!.id!!)
            it.context.startActivity(intent)
        }
    }
}