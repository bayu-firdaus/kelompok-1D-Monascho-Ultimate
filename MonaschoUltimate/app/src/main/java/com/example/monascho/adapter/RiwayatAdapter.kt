package com.example.monascho.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.MainActivityLogin
import com.example.monascho.R
import com.example.monascho.model.PayloadRiwayatTransaksi
import com.example.monascho.model.ResponsePostKeranjang
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.lacakpesanan.DialogLacakPesanan
import com.example.monascho.ui.pembayaran.PembayaranActivity
import com.example.monascho.ui.riwayattrannsaksidetail.RiwayatDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatAdapter : RecyclerView.Adapter<RiwayatAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadRiwayatTransaksi>? = null

    constructor(context: Context, data: ArrayList<PayloadRiwayatTransaksi>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_riwayat_transaksi, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTglTransaksi:TextView = itemView.findViewById(R.id.tvTglTransaksi)
        var tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
        var tvTotal:TextView = itemView.findViewById(R.id.tvTotal)
        var btnBayar:Button = itemView.findViewById(R.id.btnBayar)
        var btnSampai:Button = itemView.findViewById(R.id.btnSampai)
        var cardView:CardView = itemView.findViewById(R.id.cardView)
        var img:ImageView = itemView.findViewById(R.id.img)
        var viewLacak:LinearLayout = itemView.findViewById(R.id.viewLacak)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {

        holder.btnBayar.setOnClickListener {
            var intent = Intent(it.context, PembayaranActivity::class.java)
            intent.putExtra("id_transaksi", dataList?.get(position)!!.id_transaksi!!)
            it.context.startActivity(intent)
        }

        holder.btnSampai.setOnClickListener {
            dialogPesananDiterima(dataList?.get(position)!!.id_transaksi!!)
        }

        holder.tvTglTransaksi.text = dataList?.get(position)!!.tgl_transaksi!!
        holder.tvStatus.text = dataList?.get(position)!!.status!!
//        holder.tvTotal.text = ("Total = ${FormatRp.parsingRupiah((dataList?.get(position)!!.total!!.toInt()))}")
        holder.tvTotal.text = "Total = "+(dataList?.get(position)!!.total!!)

        when {
            dataList?.get(position)!!.status!! == "0" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#ff4d6b"))
                holder.tvStatus.text = "Menunggu Konfirmasi"
            }
            dataList?.get(position)!!.status!! == "1" -> {
                holder.btnBayar.visibility = View.VISIBLE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.tvStatus.text = "Menunggu Pembayaran"
            }
            dataList?.get(position)!!.status!! == "2" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.tvStatus.text = "Menunggu Konfirmasi Pembayaran"
            }
            dataList?.get(position)!!.status!! == "3" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#1CD66C"))
                holder.tvStatus.text = "Pembayaran DiKonfrimasi"
            }
            dataList?.get(position)!!.status!! == "4" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.tvStatus.text = "Dalam Proses Pengemesan"
            }
            dataList?.get(position)!!.status!! == "5" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.VISIBLE
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.tvStatus.text = "Pesanan Diantar"
            }
            dataList?.get(position)!!.status!! == "6" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#1CD66C"))
                holder.tvStatus.text = "Pesanan Sudah Diterima"
            }
            dataList?.get(position)!!.status!! == "7" -> {
                holder.btnBayar.visibility = View.GONE
                holder.btnSampai.visibility = View.GONE
                holder.tvStatus.setTextColor(Color.parseColor("#1CD66C"))
                holder.tvStatus.text = "Selesai"
            }
        }

        holder.cardView.setOnClickListener {
            var intent = Intent(it.context, RiwayatDetailActivity::class.java)
            intent.putExtra("idTransaksi", dataList?.get(position)!!.id_transaksi!!)
            it.context.startActivity(intent)
        }

        holder.img.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id_transaksi", dataList?.get(position)!!.id_transaksi!!)
            val fm = (c as AppCompatActivity).supportFragmentManager
            val p = DialogLacakPesanan()
            p.arguments = bundle
            p.show(fm, "")
        }

    }

    private fun dialogPesananDiterima(idTransaksi: String) {
        val dialog = Dialog(c!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_pesanan_diterima)

        val oke = dialog.findViewById<RelativeLayout>(R.id.btnOke)
        val batal = dialog.findViewById<RelativeLayout>(R.id.btnBatal)
        dialog.show()

        oke.setOnClickListener {
            getResponse(idTransaksi)
            dialog.dismiss()
        }

        batal.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun getResponse(idTransaksi: String) {
        val api = InitRetrofit().getInitInstance()
        api.postSampai(idTransaksi).enqueue(object : Callback<ResponsePostKeranjang> {
            override fun onResponse(call: Call<ResponsePostKeranjang>,response: Response<ResponsePostKeranjang>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
//                        var intent = Intent(c, MainActivityLogin::class.java)
//                        intent.putExtra("first", 1);
//                        c!!.startActivity(intent)
                        var intent = Intent(c, MainActivityLogin::class.java)
                        intent.putExtra("first", 1);
                        intent.putExtra("id_transaksi", idTransaksi)
                        intent.putExtra("ratting", "true")
                        c!!.startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePostKeranjang>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}
