package com.example.monascho
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
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
import com.example.monascho.model.ResponseLogin
import com.example.monascho.model.ResponseSignUp
import com.example.monascho.network.InitRetrofit
import com.example.monascho.ui.informasi.FragmentInformasi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private var lastPressedTime: Long = 0
    private val PERIOD = 2000

    private var user: String = ""
    private var nama: String = ""
    private var pass: String = ""
    private var strToken: String = ""

    private lateinit var etKecamatan: EditText
    private lateinit var etJk: EditText
    private lateinit var etTglLahir: EditText

    private var mYear = 0
    private var mMonth:Int = 0
    private  var mDay:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic("makeup").addOnSuccessListener {
            Log.d("subscribe", "Success")
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAGstoken", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            strToken = token.toString()
//            Toast.makeText(this, "bb$strToken", Toast.LENGTH_LONG).show()
            Log.d("TAGstoken", token.toString())
        })

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawerLayout= findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_riwayat, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(this@MainActivity)
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
                supportActionBar?.title = "Informasi"
                fragment = FragmentInformasi()
            }
            R.id.nav_login -> {
                dialogLogin()
            }
            R.id.nav_regis -> {
                dialogRegis()
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

    private fun dialogLogin() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_login)

        val btnLogin = dialog.findViewById<Button>(R.id.btnLogin)
        val etUsername = dialog.findViewById<EditText>(R.id.etUsername)
        val etPassword = dialog.findViewById<EditText>(R.id.etPassword)
        dialog.show()

        btnLogin.setOnClickListener {
            user = etUsername.text.toString()
            pass = etPassword.text.toString()

            when {
                user == "" -> {
                    etUsername.error = "Tidak boleh kosong"
                }
                pass == "" -> {
                    etPassword.error = "Tidak boleh kosong"
                }
                else -> {
                    btnLogin.visibility = View.GONE

                    val api = InitRetrofit().getInitInstance()
                    api.login(user, pass, strToken).enqueue(object :
                        Callback<ResponseLogin> {
                        override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                            if (response.isSuccessful) {
                                val jsonresponse = response.body()?.payload
                                if (user == jsonresponse?.no_telp) {

                                    getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
                                        .edit()
                                        .putString("code", jsonresponse.id_konsumen)
                                        .putString("username", jsonresponse.no_telp)
                                        .putString("password", jsonresponse.password)
                                        .putString("nama", jsonresponse.nama)
                                        .apply()

                                        val myIntent = Intent(this@MainActivity, MainActivityLogin::class.java)
                                        startActivity(myIntent)
                                        finish()
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Login Gagal, Periksa kembali Username dan Password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    btnLogin.visibility = View.VISIBLE
                                }
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Login Gagal, Periksa kembali Username dan Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                                btnLogin.visibility = View.VISIBLE
                            }
                        }

                        override fun onFailure(call: Call<ResponseLogin>, error: Throwable) {
                            Log.e("android1", "errornya ${error.message}")
                        }
                    })
                }
            }
        }
    }

    private fun dialogRegis() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_regis)

        val btnLogin = dialog.findViewById<Button>(R.id.btnLogin)
        val etUsername = dialog.findViewById<EditText>(R.id.etUsername)
        val etNama = dialog.findViewById<EditText>(R.id.etNama)
//        etKecamatan = dialog.findViewById(R.id.etKecamatan)
        val etAlamat = dialog.findViewById<EditText>(R.id.etAlamat)
//        val etKodepos = dialog.findViewById<EditText>(R.id.etKodepos)
        etTglLahir = dialog.findViewById(R.id.etTglLahir)
        etJk = dialog.findViewById(R.id.etJk)
        val etEmail = dialog.findViewById<EditText>(R.id.etEmail)
        val etPassword = dialog.findViewById<EditText>(R.id.etPassword)
        dialog.show()

        initJk()
//        initKec()
        initTgl()

        btnLogin.setOnClickListener {
            user = etUsername.text.toString()
            nama = etNama.text.toString()
            pass = etPassword.text.toString()

            when {
//                etKecamatan.text.toString() == "" -> {
//                    etKecamatan.error = "Tidak boleh kosong"
//                }
                etAlamat.text.toString() == "" -> {
                    etAlamat.error = "Tidak boleh kosong"
                }
//                etKodepos.text.toString() == "" -> {
//                    etKodepos.error = "Tidak boleh kosong"
//                }
                etTglLahir.text.toString() == "" -> {
                    etTglLahir.error = "Tidak boleh kosong"
                }
                etJk.text.toString() == "" -> {
                    etJk.error = "Tidak boleh kosong"
                }
                etEmail.text.toString() == "" -> {
                    etEmail.error = "Tidak boleh kosong"
                }
                user == "" -> {
                    etUsername.error = "Tidak boleh kosong"
                }
                user == "" -> {
                    etNama.error = "Tidak boleh kosong"
                }
                pass == "" -> {
                    etPassword.error = "Tidak boleh kosong"
                }
                else -> {
                    btnLogin.visibility = View.GONE
                    val api = InitRetrofit().getInitInstance()
                    api.regis(
                            user,
                            nama,
//                            etKecamatan.text.toString(),
                            etAlamat.text.toString(),
//                            etKodepos.text.toString(),
                            etTglLahir.text.toString(),
                            etJk.text.toString(),
                            etEmail.text.toString(),
                            pass).enqueue(object :
                        Callback<ResponseSignUp> {
                        override fun onResponse(call: Call<ResponseSignUp>, response: Response<ResponseSignUp>) {
                            Log.d("responseRegis", response.toString())
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Registrasi Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Registrasi Gagal, Terjadi Kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                                btnLogin.visibility = View.VISIBLE
                            }
                        }

                        override fun onFailure(call: Call<ResponseSignUp>, error: Throwable) {
                            Log.e("android1", "errornya ${error.message}")
                            Toast.makeText(
                                this@MainActivity,
                                "Registrasi Error, Terjadi Kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }

    private fun initKec() {
        etKecamatan.inputType = InputType.TYPE_NULL
        etKecamatan.isClickable = true
        etKecamatan.setOnFocusChangeListener { view, b ->
            if(b){
                dialogKec()
            }
        }
        etKecamatan.setOnClickListener {
            dialogKec()
        }
    }

    private fun dialogKec () {
        val singleItems = arrayOf("Kota Barat", "Dungingi", "Kota Selatan", "Kota Tengah", "Kota Timur", "Kota Utara", "Sipatana")
        val checkedItem = -1
        var selectedItems = ""

        MaterialAlertDialogBuilder(this)
                .setTitle("Pilih Kecamatan")
                .setNeutralButton("Batal") { dialog, which ->
                    // Respond to neutral button press
                    selectedItems = ""
                }
                .setPositiveButton("Pilih") { dialog, which ->
                    // Respond to positive button press
                    etKecamatan.setText("")
                    etKecamatan.setText(selectedItems)
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
        etTglLahir.inputType = InputType.TYPE_NULL
        etTglLahir.isClickable = true
        etTglLahir.setOnFocusChangeListener { view, b ->
            if(b){
                dialogTglLahir()
            }
        }
        etTglLahir.setOnClickListener {
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

                    etTglLahir.setText("$year-$bulan-$hari")
                }, mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    private fun initJk() {
        etJk.inputType = InputType.TYPE_NULL
        etJk.isClickable = true
        etJk.setOnFocusChangeListener { view, b ->
            if(b){
                dialogJk()
            }
        }
        etJk.setOnClickListener {
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
                    etJk.setText("")
                    etJk.setText(selectedItems)
                    selectedItems = ""
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                    selectedItems = singleItems?.get(which) ?: ""
                }
                .show()
    }
}