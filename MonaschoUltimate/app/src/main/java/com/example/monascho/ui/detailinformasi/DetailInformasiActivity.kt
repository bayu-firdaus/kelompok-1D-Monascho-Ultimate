package com.example.monascho.ui.detailinformasi

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.monascho.databinding.ActivityDetailInformasiBinding
import com.example.monascho.model.PayloadDetailInformasi
import com.example.monascho.network.InitRetrofit
import com.squareup.picasso.Picasso

class DetailInformasiActivity : AppCompatActivity(), InformasiDetailView {

    private lateinit var binding: ActivityDetailInformasiBinding
    lateinit var informasiDetailPresenter: InformasiDetailPresenter
    private var ids = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInformasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.swlayout.setOnRefreshListener {
            binding.swlayout.isRefreshing = false
            binding.progressBar.visibility = View.VISIBLE
            informasiDetailPresenter.getResponse(ids)
        }

        informasiDetailPresenter = InformasiDetailPresenter(this)
        ids = intent.getStringExtra("id").toString()
        informasiDetailPresenter.getResponse(ids)
        
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onSuccess(payloadDetailInformasi: PayloadDetailInformasi) {
        binding.progressBar.visibility = View.GONE
        binding.tvNama.text = payloadDetailInformasi.judul
        binding.tvStok.text = payloadDetailInformasi.tgl
        binding.tvDeskripsi.text = Html.fromHtml(payloadDetailInformasi.ket)

        val api = InitRetrofit().getFolderImg()
        Picasso.with(this).load("$api${payloadDetailInformasi.foto}").into(binding.foto)
    }

    override fun onErrorResponse() {
        binding.progressBar.visibility = View.GONE
    }
}