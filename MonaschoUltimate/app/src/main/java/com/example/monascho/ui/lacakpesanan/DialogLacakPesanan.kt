package com.example.monascho.ui.lacakpesanan

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monascho.R
import com.example.monascho.adapter.LacakPesananAdapter
import com.example.monascho.model.PayloadLacakPesanan


class DialogLacakPesanan : DialogFragment(), LacakPesananView {
    private var idTransaksi = ""
    private lateinit var lacakPesananPresenter: LacakPesananPresenter
    private lateinit var lacakPesananAdapter: LacakPesananAdapter
    private lateinit var recycler : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val android = inflater.inflate(R.layout.fragment_lackpesanan, container, false)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        idTransaksi = requireArguments().getString("id_transaksi").toString()

        recycler = android.findViewById(R.id.recycler)
        lacakPesananPresenter = LacakPesananPresenter(this)
        lacakPesananPresenter.getResponse(idTransaksi)

        return android
    }

    override fun onSuccess(payloadLacakPesanan: ArrayList<PayloadLacakPesanan>?) {
//        bindings!!.progressBar.visibility = View.GONE
        lacakPesananAdapter = LacakPesananAdapter(requireContext(), payloadLacakPesanan)
        recycler?.adapter = lacakPesananAdapter
        recycler?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    override fun onErrorResponse() {

    }
}