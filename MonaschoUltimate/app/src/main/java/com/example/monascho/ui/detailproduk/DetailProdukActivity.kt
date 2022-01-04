package com.example.monascho.ui.detailproduk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monascho.R
import com.example.monascho.databinding.ActivityDetailProdukBinding
import com.example.monascho.model.PayloadProdukDetail
import com.example.monascho.model.ResponsePostKeranjang
import com.example.monascho.network.InitRetrofit
import com.example.monascho.utils.FormatRp
import com.example.monascho.utils.OnSwipeTouchListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProdukActivity : AppCompatActivity(), ProdukDetailView {

    private lateinit var binding: ActivityDetailProdukBinding
    lateinit var produkDetailPresenter: ProdukDetailPresenter
    private var jum: Int = 1
    private var ids = ""
    private var idUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val pref: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        binding.swlayout.setOnRefreshListener {
            binding.swlayout.isRefreshing = false
            binding.progressBar.visibility = View.VISIBLE
            produkDetailPresenter.getResponse(ids)
        }

        produkDetailPresenter = ProdukDetailPresenter(this)
        ids = intent.getStringExtra("id").toString()
        produkDetailPresenter.getResponse(ids)

        binding.tvJumlah.inputType = InputType.TYPE_NULL
        jum = Integer.valueOf("" + binding.tvJumlah.text.toString())
//        iniToolbarLayout()

        binding.imgKurang.setOnClickListener {
            jum = Integer.valueOf("" + binding!!.tvJumlah.text.toString())
            if (jum == 1) {
            } else if (jum > 1) {
                try {
                    jum -= 1
                    binding!!.tvJumlah.text = "" + jum
                } catch (e: Exception) {
                }
            }
        }

        binding.imgTambah.setOnClickListener {
            jum = Integer.valueOf(binding.tvJumlah.text.toString())

            try {
                jum += 1
                binding.tvJumlah.text = jum.toString()
            } catch (e: java.lang.Exception) {
            }
        }

        binding.btnAddTocart.setOnClickListener {
            if (jum == 0) {
                Toast.makeText(
                    this@DetailProdukActivity,
                    "Tentukan nilai item",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                binding!!.progressBar.visibility = View.VISIBLE
                postKeranjang(idUser,ids,jum.toString())
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            binding.vlipper.showNext()
        }

        binding.btnPrev.setOnClickListener {
            binding.vlipper.showPrevious()
        }

        binding.foto.setOnTouchListener(object : OnSwipeTouchListener(this@DetailProdukActivity){
            override fun onSwipeRight() {
                binding.vlipper.showPrevious()
            }
            override fun onSwipeLeft() {
                binding.vlipper.showNext()
            }
        })

        binding.foto2.setOnTouchListener(object : OnSwipeTouchListener(this@DetailProdukActivity){
            override fun onSwipeRight() {
                binding.vlipper.showPrevious()
            }
            override fun onSwipeLeft() {
                binding.vlipper.showNext()
            }
        })

        binding.foto3.setOnTouchListener(object : OnSwipeTouchListener(this@DetailProdukActivity){
            override fun onSwipeRight() {
                binding.vlipper.showPrevious()
            }
            override fun onSwipeLeft() {
                binding.vlipper.showNext()
            }
        })

        binding.foto4.setOnTouchListener(object : OnSwipeTouchListener(this@DetailProdukActivity){
            override fun onSwipeRight() {
                binding.vlipper.showPrevious()
            }
            override fun onSwipeLeft() {
                binding.vlipper.showNext()
            }
        })

        binding.foto5.setOnTouchListener(object : OnSwipeTouchListener(this@DetailProdukActivity){
            override fun onSwipeRight() {
                binding.vlipper.showPrevious()
            }
            override fun onSwipeLeft() {
                binding.vlipper.showNext()
            }
        })

//        binding.fab.setOnClickListener {
//            val myIntent = Intent(applicationContext, KeranjangActivity::class.java)
//            startActivity(myIntent)
//        }

    }

//    private fun iniToolbarLayout() {
//        binding.appBar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
//            var isShow = true
//            var scrollRange = -1
//            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.totalScrollRange
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    binding.nmProduk.setTextColor(resources.getColor(R.color.colorDarkGrey))
//                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//                    binding.fab.visibility = View.INVISIBLE
//                    isShow = true
//                } else if (isShow) {
//                    binding.nmProduk.setTextColor(resources.getColor(R.color.white))
//                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
//                    val nowtime = System.currentTimeMillis() / 1000L
//                    binding.fab.visibility = View.VISIBLE
//                    isShow = false
//                }
//            }
//        })
//    }

    override fun onSuccess(payloadProdukDetail: PayloadProdukDetail) {
        if (payloadProdukDetail.stok == "0") {
            binding!!.tvJumlah.text = "0"
            binding!!.imgKurang.isEnabled = false
            binding!!.imgTambah.isEnabled = false
        }

        if(payloadProdukDetail.diskon.toInt() > 0) {
            binding!!.tvDiskon.visibility = View.VISIBLE
        }else{
            binding!!.tvDiskon.visibility = View.GONE
        }

        binding!!.tvDiskon.text = "Diskon "+payloadProdukDetail.diskon+"%"

        binding.progressBar.visibility = View.GONE
        binding.tvNama.text = payloadProdukDetail.nama
        binding.tvStok.text = "Stok "+payloadProdukDetail.stok
        binding.tvHarga.text = "${FormatRp.parsingRupiah(payloadProdukDetail.harga.toInt())}"
        binding.tvDeskripsi.text = payloadProdukDetail.deskripsi

        val api = InitRetrofit().getFolderImgProduk()
        Picasso.with(this).load("$api${payloadProdukDetail.foto}").error(R.drawable.no_img).into(binding.foto)
        Picasso.with(this).load("$api${payloadProdukDetail.foto2}").error(R.drawable.no_img).into(binding.foto2)
        Picasso.with(this).load("$api${payloadProdukDetail.foto3}").error(R.drawable.no_img).into(binding.foto3)
        Picasso.with(this).load("$api${payloadProdukDetail.foto4}").error(R.drawable.no_img).into(binding.foto4)
        Picasso.with(this).load("$api${payloadProdukDetail.foto5}").error(R.drawable.no_img).into(binding.foto5)
    }

    override fun onErrorResponse() {
        binding.progressBar.visibility = View.GONE
    }
//
    private fun postKeranjang (idKonsumen:String, idProduk:String, KuantitasItem:String) {
        val api = InitRetrofit().getInitInstance()
        api.postKeranjang(idKonsumen,idProduk,KuantitasItem).enqueue(object :
                Callback<ResponsePostKeranjang> {
            override fun onResponse(call: Call<ResponsePostKeranjang>, response: Response<ResponsePostKeranjang>) {
                if (response.isSuccessful) {
                    if (response.body()!!.payload == "habis") {
                        Toast.makeText(
                                this@DetailProdukActivity,
                                "Stok Tidak Cukup",
                                Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                                this@DetailProdukActivity,
                                "Berhasil ditambahkan dikeranjang",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    binding!!.progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(
                        this@DetailProdukActivity,
                        "Terjadi kesalahan, harap coba kembali",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding!!.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponsePostKeranjang>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }



}