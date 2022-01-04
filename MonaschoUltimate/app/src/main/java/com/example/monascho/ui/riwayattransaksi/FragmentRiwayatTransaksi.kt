package com.example.monascho.ui.riwayattransaksi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monascho.R
import com.example.monascho.adapter.RiwayatAdapter
import com.example.monascho.databinding.FragmentTransaksiBinding
import com.example.monascho.model.PayloadRiwayatTransaksi

class FragmentRiwayatTransaksi : Fragment(R.layout.fragment_transaksi), RiwayatTransaksiView {
    private var bindings: FragmentTransaksiBinding? = null
    lateinit var riwayatTransaksiPresenter: RiwayatTransaksiPresenter
    private lateinit var riwayatAdapter: RiwayatAdapter
    private var idUser = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTransaksiBinding.bind(view)
        bindings = binding

        val pref: SharedPreferences = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        binding!!.recycler.isFocusable = false
        riwayatTransaksiPresenter = RiwayatTransaksiPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            bindings!!.progressBar.visibility = View.VISIBLE
            riwayatTransaksiPresenter.getResponse(idUser)
        }
    }

    override fun onStart() {
        super.onStart()
        riwayatTransaksiPresenter.getResponse(idUser)
    }

    override fun onSuccess(payloadRiwayatTransaksi: ArrayList<PayloadRiwayatTransaksi>?) {
        bindings!!.progressBar.visibility = View.GONE
        riwayatAdapter = RiwayatAdapter(requireContext(), payloadRiwayatTransaksi)
        bindings?.recycler?.adapter = riwayatAdapter
        bindings?.recycler?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    override fun onErrorResponse() {
        bindings!!.progressBar.visibility = View.GONE
        Toast.makeText(
                requireContext(),
                "Data Kosong",
                Toast.LENGTH_SHORT
        ).show()
    }
}