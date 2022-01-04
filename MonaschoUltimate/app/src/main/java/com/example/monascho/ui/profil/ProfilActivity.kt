package com.example.monascho.ui.profil

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monascho.databinding.ActivityProfilBinding
import com.example.monascho.model.PayloadProfil
import com.example.monascho.model.ResponsePostKeranjang
import com.example.monascho.network.InitRetrofit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ProfilActivity : AppCompatActivity(), ProfilView {

    private lateinit var binding: ActivityProfilBinding
    lateinit var profilPresenter: ProfilPresenter
    private var idUser = ""
    private var mYear = 0
    private var mMonth:Int = 0
    private  var mDay:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val pref: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        profilPresenter = ProfilPresenter(this)
        profilPresenter.getResponse(idUser)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            when {
                binding.etNama.text.toString() == "" -> {
                    binding.etNama.error = "Tidak boleh kosong"
                }
                binding.etJk.text.toString() == "" -> {
                    binding.etJk.error = "Tidak boleh kosong"
                }
                binding.etTglLahir.text.toString() == "" -> {
                    binding.etTglLahir.error = "Tidak boleh kosong"
                }
                binding.etKodepos.text.toString() == "" -> {
                    binding.etKodepos.error = "Tidak boleh kosong"
                }
                binding.etKecamatan.text.toString() == "" -> {
                    binding.etKecamatan.error = "Tidak boleh kosong"
                }
                binding.etAlamat.text.toString() == "" -> {
                    binding.etAlamat.error = "Tidak boleh kosong"
                }
                else -> {
                    updateProfil()
            }
            }

        }

    }

    override fun onSuccess(payloadProfil: PayloadProfil) {
        binding.progressBar.visibility = View.GONE
        binding.etNama.setText(payloadProfil.nama)
        binding.etJk.setText(payloadProfil.jk)
        initJk()
        binding.etEmail.setText(payloadProfil.email)
        initTgl()
        binding.etTglLahir.setText(payloadProfil.tgl_lahir)
        binding.etKodepos.setText(payloadProfil.kodepos)
        initKec()
        binding.etKecamatan.setText(payloadProfil.kecamatan)
        binding.etAlamat.setText(payloadProfil.alamat)
    }

    override fun onErrorResponse() {

    }

    private fun initKec() {
        binding.etKecamatan.inputType = InputType.TYPE_NULL
        binding.etKecamatan.isClickable = true
        binding.etKecamatan.setOnFocusChangeListener { view, b ->
            if(b){
                dialogKec()
            }
        }
        binding.etKecamatan.setOnClickListener {
            dialogKec()
        }
    }

    private fun dialogKec () {
        val singleItems = arrayOf("Giri", "Banyuwangi", "Rogojampi", "Glagah", "Genteng", "Kabat", "Songgon")
        val checkedItem = -1
        var selectedItems = ""

        MaterialAlertDialogBuilder(this)
                .setTitle("Pilih Jenis Kecamatan")
                .setNeutralButton("Batal") { dialog, which ->
                    // Respond to neutral button press
                    selectedItems = ""
                }
                .setPositiveButton("Pilih") { dialog, which ->
                    // Respond to positive button press
                    binding.etKecamatan.setText("")
                    binding.etKecamatan.setText(selectedItems)
                    selectedItems = ""
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                    selectedItems = singleItems?.get(which) ?: ""
                }
                .show()
    }

    private fun initTgl() {
        binding.etTglLahir.inputType = InputType.TYPE_NULL
        binding.etTglLahir.isClickable = true
        binding.etTglLahir.setOnFocusChangeListener { view, b ->
            if(b){
                dialogTglLahir()
            }
        }
        binding.etTglLahir.setOnClickListener {
            dialogTglLahir()
        }
    }

    private fun dialogTglLahir() {
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    var hari = ""
                    hari = if (dayOfMonth >= 10) {
                        dayOfMonth.toString()
                    } else {
                        "0$dayOfMonth"
                    }

                    var month = (monthOfYear + 1)
                    var bulan = ""
                    bulan = if (month >= 10) {
                        month.toString()
                    } else {
                        "0$month"
                    }

                    binding.etTglLahir.setText("$year-$bulan-$hari")
                }, mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    private fun initJk() {
        binding.etJk.inputType = InputType.TYPE_NULL
        binding.etJk.isClickable = true
        binding.etJk.setOnFocusChangeListener { view, b ->
            if(b){
                dialogJk()
            }
        }
        binding.etJk.setOnClickListener {
            dialogJk()
        }
    }

    private fun dialogJk () {
        val singleItems = arrayOf("Laki-Laki", "Perempuan")
        val checkedItem = -1
        var selectedItems = ""

        MaterialAlertDialogBuilder(this)
                .setTitle("Pilih Jenis Kelamin")
                .setNeutralButton("Batal") { dialog, which ->
                    // Respond to neutral button press
                    selectedItems = ""
                }
                .setPositiveButton("Pilih") { dialog, which ->
                    // Respond to positive button press
                    binding.etJk.setText("")
                    binding.etJk.setText(selectedItems)
                    selectedItems = ""
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                    selectedItems = singleItems?.get(which) ?: ""
                }
                .show()
    }

    fun updateProfil() {
        val api = InitRetrofit().getInitInstance()
        api.updateProfil(
                idUser,
                binding.etNama.text.toString(),
                binding.etKecamatan.text.toString(),
                binding.etAlamat.text.toString(),
                binding.etKodepos.text.toString(),
                binding.etTglLahir.text.toString(),
                binding.etJk.text.toString(),
                binding.etEmail.text.toString(),
        ).enqueue(object : Callback<ResponsePostKeranjang> {
            override fun onResponse(call: Call<ResponsePostKeranjang>, response: Response<ResponsePostKeranjang>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        finish()
                        Toast.makeText(this@ProfilActivity, "Update Profil Berhasil", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@ProfilActivity, "Gagal terjadi kesalahan", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this@ProfilActivity, "Gagal terjadi kesalahan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostKeranjang>, error: Throwable) {
                Toast.makeText(this@ProfilActivity, "Gagal terjadi kesalahan", Toast.LENGTH_LONG).show()
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}