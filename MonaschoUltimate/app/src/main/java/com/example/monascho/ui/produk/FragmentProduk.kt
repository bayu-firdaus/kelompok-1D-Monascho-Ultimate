package com.example.monascho.ui.produk

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monascho.R
import com.example.monascho.adapter.ProdukAdapter
import com.example.monascho.databinding.FragmentProdukBinding
import com.example.monascho.model.PayloadProduk

class FragmentProduk : Fragment(R.layout.fragment_produk), ProdukView {
    private var bindings: FragmentProdukBinding? = null
    lateinit var produkPresenter: ProdukPresenter
    private lateinit var produkAdapter: ProdukAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProdukBinding.bind(view)
        bindings = binding

        binding!!.recycler.isFocusable = false
        produkPresenter = ProdukPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            bindings!!.swlayout.visibility = View.VISIBLE
            produkPresenter.getResponse("")
        }

        binding!!.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isEmpty()) {
                    produkPresenter.getResponse("")
                }
                if (charSequence.length > 2) {
                    produkPresenter.getResponse(charSequence.toString())
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onStart() {
        super.onStart()
        produkPresenter.getResponse("")
    }

    override fun onSuccess(payloadProduk: ArrayList<PayloadProduk>?) {
        produkAdapter = ProdukAdapter(requireContext(), payloadProduk)
        bindings?.recycler?.adapter = produkAdapter
        bindings?.recycler?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onErrorResponse() {
        Toast.makeText(
                requireContext(),
                "Data Kosong",
                Toast.LENGTH_SHORT
        ).show()
    }
}