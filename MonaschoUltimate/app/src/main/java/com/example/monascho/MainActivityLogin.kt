package com.example.monascho

import android.Manifest
import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.monascho.model.ResponsePostRatting
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.informasi.FragmentInformasi
import com.example.monascho.ui.keranjang.FragmentKeranjang
import com.example.monascho.ui.produk.FragmentProduk
import com.example.monascho.ui.profil.ProfilActivity
import com.example.monascho.ui.riwayattransaksi.FragmentRiwayatTransaksi
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityLogin : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private var lastPressedTime: Long = 0
    private val PERIOD = 2000

    private var fragments = ""
    private var idTransaksi = ""
    lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        val pref: SharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawerLayout= findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        if (intent.getIntExtra("first", 0) == 0) {
            val navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_home, R.id.nav_riwayat, R.id.nav_slideshow), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
        else{
            val navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_home, R.id.nav_riwayat, R.id.nav_slideshow), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            val login: Fragment = FragmentRiwayatTransaksi()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, login, login.javaClass.simpleName).addToBackStack(null)
                    .commit()
        }

        navView.setNavigationItemSelectedListener(this@MainActivityLogin)
        val header: View = navView.getHeaderView(0)
        val txtnama = header.findViewById<View>(R.id.tvName) as TextView
        val tvUsername = header.findViewById<View>(R.id.tvNik) as TextView
        val containerP = header.findViewById<View>(R.id.containerP) as LinearLayout
        txtnama.text = pref.getString("nama", null)
        tvUsername.text = pref.getString("username", null)

        containerP.setOnClickListener {
            val intent = Intent(this@MainActivityLogin, ProfilActivity::class.java)
            startActivity(intent)
        }

        idTransaksi = intent.getStringExtra("id_transaksi").toString()
        if (intent.getStringExtra("ratting").toString() == "true") {
            dialogRating()
        }else{

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            when (event.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (event.downTime - lastPressedTime < PERIOD) {
                        val baIntent = Intent(Intent.ACTION_MAIN)
                        baIntent.addCategory(Intent.CATEGORY_HOME)
                        baIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(baIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Tekan sekali lagi untuk keluar.",
                            Toast.LENGTH_SHORT
                        ).show()
                        lastPressedTime = event.eventTime
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_informasi -> {
                supportActionBar?.title = "Informasi Umum"
                fragment = FragmentInformasi()
            }
            R.id.nav_produk -> {
                supportActionBar?.title = "Produk"
                fragment = FragmentProduk()
            }
            R.id.nav_keranjang -> {
                supportActionBar?.title = "Keranjang"
                fragment = FragmentKeranjang()
            }
            R.id.nav_riwayat -> {
                supportActionBar?.title = "Riwayat Pesanan"
                fragment = FragmentRiwayatTransaksi()
            }
            R.id.nav_chat -> {
                val url = "https://api.whatsapp.com/send?phone=+6282187269421"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            R.id.nav_log_out -> {
                dialogSignOut()
            }
        }
        if (fragment != null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, fragment)
            ft.commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun requestPermission() {

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {

                    } else {
                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(this@MainActivityLogin, "Untuk mengakses Menu-menu Iznkan Aplikasi dipengaturan", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@MainActivityLogin, "Untuk memaksimalkan Aplikasi, Harap izinkan beberapa izin yang tampil", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }).check()
    }

    private fun dialogSignOut() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_signout)

        val aksi = dialog.findViewById<RelativeLayout>(R.id.signout)
        dialog.show()

        aksi.setOnClickListener {

            val pref: SharedPreferences = getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            )
            pref.edit().clear().commit()

            val intent = Intent(this@MainActivityLogin, CloseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            dialog.dismiss()
        }
    }

    private fun dialogRating() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_ratting)

        val rattingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)
        val etKet = dialog.findViewById<EditText>(R.id.etket)
        val oke = dialog.findViewById<RelativeLayout>(R.id.btnOke)
        val batal = dialog.findViewById<RelativeLayout>(R.id.btnBatal)
        dialog.show()

        oke.setOnClickListener {
            getResponse(idTransaksi, rattingBar.rating.toString(), etKet.text.toString())
            dialog.dismiss()
        }

        batal.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun getResponse(idTransaksi: String, rating: String, komentar: String) {
        val api = InitRetrofit().getInitInstance()
        api.postRating(idTransaksi, rating, komentar).enqueue(object : Callback<ResponsePostRatting> {
            override fun onResponse(
                call: Call<ResponsePostRatting>,
                response: Response<ResponsePostRatting>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        dialog.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePostRatting>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}