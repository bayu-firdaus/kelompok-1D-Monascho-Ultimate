package com.example.monascho.ui.keranjang

import android.content.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monascho.R
import com.example.monascho.adapter.KeranjangAdapter
import com.example.monascho.databinding.FragmentKeranjangBinding
import com.example.monascho.model.PayloadKeranjang
import com.example.monascho.ui.pilihlokasi.PilihLokasiActivity
import com.example.monascho.utils.FormatRp
import com.example.monascho.utils.TmpData
import java.lang.Exception

class FragmentKeranjang : Fragment(R.layout.fragment_keranjang), KeranjangView {

    private var bindings: FragmentKeranjangBinding? = null
    lateinit var keranjangPresenter: KeranjangPresenter
    private lateinit var keranjangAdapter: KeranjangAdapter
    private var idUser = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentKeranjangBinding.bind(view)
        bindings = binding

        val pref: SharedPreferences = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

//        pb1 = bindings!!.findViewById(R.id.progressBar)
        bindings!!.recycler.isFocusable = false
        keranjangPresenter = KeranjangPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            keranjangPresenter.getResponse(idUser)
        }

        bindings!!.btnPembayaran.setOnClickListener {
            if (TmpData.idProduk.isEmpty()) {
                Toast.makeText(requireContext(), "tidak ada data yang dipilih", Toast.LENGTH_LONG).show()
            }else{
                dialog(requireContext())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        TmpData.idProduk.clear()
        TmpData.kuantitas.clear()
        keranjangPresenter.getResponse(idUser)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                mMessageReceiver,
                IntentFilter("MyData")
        )
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val refresh = intent.getStringExtra("refresh").toString()
            if (refresh == "1") {
                keranjangPresenter.getResponse(idUser)
                bindings!!.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onSuccess(payloadKeranjang: ArrayList<PayloadKeranjang>?, total: String) {
        try {
            bindings!!.tvTotal.text = FormatRp.parsingRupiah(total.toInt())
            bindings!!.progressBar.visibility = View.GONE
            bindings!!.lcart.visibility = View.VISIBLE
            keranjangAdapter = KeranjangAdapter(requireContext(), payloadKeranjang)
            bindings!!.recycler?.adapter = keranjangAdapter
            bindings!!.recycler?.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            )
        }catch (e:Exception) {

        }
    }

    override fun onErrorResponse() {
        bindings!!.progressBar.visibility = View.GONE
    }

    private fun dialog(context: Context) {
        val options = arrayOf<CharSequence>("TRANSFER", "COD", "Batal")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Pilih Metode Pembayaran")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            when {
                options[item] == "TRANSFER" -> {
                    val myIntent = Intent(requireContext(), PilihLokasiActivity::class.java)
                    myIntent.putExtra("pembayaran", "1")
                    startActivity(myIntent)
                }
                options[item] == "COD" -> {
                    val myIntent = Intent(requireContext(), PilihLokasiActivity::class.java)
                    myIntent.putExtra("pembayaran", "2")
                    startActivity(myIntent)
                }
                options[item] == "Batal" -> {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }
}