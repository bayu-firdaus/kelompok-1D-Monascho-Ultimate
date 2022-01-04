package com.example.monascho.ui.riwayattrannsaksidetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monascho.adapter.RiwayatDetailAdapter
import com.example.monascho.databinding.ActivityRiwayatTransaksiDetailBinding
import com.example.monascho.model.PayloadRiwayatTransaksiDetail

class RiwayatDetailActivity : AppCompatActivity(), RiwayatDetailView {

    private lateinit var binding: ActivityRiwayatTransaksiDetailBinding
    lateinit var riwayatDetailPresenter: RiwayatDetailPresenter
    private lateinit var riwayatDetailAdapter: RiwayatDetailAdapter
    private var idTransaksi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatTransaksiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        idTransaksi = intent.getStringExtra("idTransaksi").toString()

        binding!!.recycler.isFocusable = false
        riwayatDetailPresenter = RiwayatDetailPresenter(this)

        binding!!.swlayout.setOnRefreshListener {
            binding!!.swlayout.isRefreshing = false
            riwayatDetailPresenter.getResponse(idTransaksi)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        riwayatDetailPresenter.getResponse(idTransaksi)
    }

    override fun onSuccess(payloadRiwayatTransaksiDetail: ArrayList<PayloadRiwayatTransaksiDetail>?, ongkir:String, subTotal:String, total:String) {
        binding.tvOngkir.text = ongkir
        binding.tvSubTotal.text = subTotal
        binding.tvTotal.text = total

        binding.progressBar.visibility = View.GONE
        riwayatDetailAdapter = RiwayatDetailAdapter(this, payloadRiwayatTransaksiDetail)
        binding?.recycler?.adapter = riwayatDetailAdapter
        binding?.recycler?.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    override fun onErrorResponse() {
        binding.progressBar.visibility = View.GONE
    }
}