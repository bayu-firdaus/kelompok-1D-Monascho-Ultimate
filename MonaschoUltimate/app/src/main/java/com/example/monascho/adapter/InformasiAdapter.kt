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
import com.example.monascho.model.PayloadInformasi
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.detailinformasi.DetailInformasiActivity
import com.squareup.picasso.Picasso


class InformasiAdapter : RecyclerView.Adapter<InformasiAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadInformasi>? = null

    constructor(context: Context, data: ArrayList<PayloadInformasi>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_informasi, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder as MyViewHolder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul:TextView = itemView.findViewById(R.id.tvJudul)
        var tvTgl:TextView = itemView.findViewById(R.id.tvTgl)
        var img:ImageView = itemView.findViewById(R.id.img)
        var cardView:CardView = itemView.findViewById(R.id.cardView)
    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvJudul.text = dataList?.get(position)!!.judul!!
        holder.tvTgl.text = dataList?.get(position)!!.tgl!!

        val api = InitRetrofit().getFolderImg()
        Picasso.with(c).load("$api${dataList?.get(position)!!.foto!!}").resize(200, 250).into(holder.img)

        holder.cardView.setOnClickListener {
            var intent = Intent(it.context, DetailInformasiActivity::class.java)
            intent.putExtra("id", dataList?.get(position)!!.id!!)
            it.context.startActivity(intent)
        }
    }
}