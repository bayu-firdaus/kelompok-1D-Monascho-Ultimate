package com.example.monascho.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.R
import com.example.monascho.model.PayloadLacakPesanan

class LacakPesananAdapter : RecyclerView.Adapter<LacakPesananAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadLacakPesanan>? = null

    constructor(context: Context, data: ArrayList<PayloadLacakPesanan>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_lacakpesanan, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTgl:TextView = itemView.findViewById(R.id.tvTgl)
        var tvJam:TextView = itemView.findViewById(R.id.tvJam)
        var tvPesan:TextView = itemView.findViewById(R.id.tvPesan)
        var tvNoHp:TextView = itemView.findViewById(R.id.noHp)
        var line1:LinearLayout = itemView.findViewById(R.id.line1)
        var line2:LinearLayout = itemView.findViewById(R.id.line2)
        var line3:LinearLayout = itemView.findViewById(R.id.line3)
//        var img:ImageView = itemView.findViewById(R.id.img)
//        var cardView:CardView = itemView.findViewById(R.id.cardBuku)
    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvTgl.text = dataList?.get(position)!!.tgl!!
        holder.tvJam.text = dataList?.get(position)!!.jam!!
        holder.tvPesan.text = dataList?.get(position)!!.pesan!!
        holder.tvNoHp.text = dataList?.get(position)!!.nomor!!

        val check = dataList?.size?.minus(position)
        if(dataList?.size!! == 1) {
            holder.line3.visibility = View.VISIBLE
        }else{
            if (check == 1) {
                holder.tvPesan.setTextColor(Color.parseColor("#00ACC1"))
                holder.line1.visibility = View.GONE
                holder.line2.visibility = View.VISIBLE
                holder.line3.visibility = View.GONE
            }else{
                holder.tvTgl.setTextColor(Color.parseColor("#9F9E9E"))
                holder.tvJam.setTextColor(Color.parseColor("#9F9E9E"))
                holder.tvTgl.textSize = 10f
                holder.tvJam.textSize = 10f
                holder.line1.visibility = View.VISIBLE
                holder.line2.visibility = View.GONE
                holder.line3.visibility = View.GONE
            }
        }

//        if (dataList?.get(position)!!.diskon!!.toInt() > 0) {
//            holder.tvDiskon.visibility = View.VISIBLE
//        }
//
//        holder.tvDiskon.text = "Diskon " +dataList?.get(position)!!.diskon!! + "%"
//
//        val api = InitRetrofit().getFolderImgProduk()
//        Picasso.with(c).load("$api${dataList?.get(position)!!.foto!!}").resize(200, 250).into(holder.img)
//
//        holder.cardView.setOnClickListener {
//            var intent = Intent(it.context, DetailProdukActivity::class.java)
//            intent.putExtra("id", dataList?.get(position)!!.id!!)
//            it.context.startActivity(intent)
//        }
    }
}