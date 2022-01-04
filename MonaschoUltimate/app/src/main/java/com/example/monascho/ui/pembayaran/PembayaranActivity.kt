package com.example.monascho.ui.pembayaran

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monascho.MainActivityLogin
import com.example.monascho.R
import com.example.monascho.databinding.ActivityPembayaranBinding
import com.example.monascho.network.Api
import com.example.monascho.network.InitRetrofit
import com.google.gson.Gson
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*


class PembayaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBinding
//    private lateinit var detailTransaksiPresenter: DetailTransaksiPresenter
    private var idTransaksi = ""
    private var idUser = ""
    private var bitmap: Bitmap? = null
    private lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val pref: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        idTransaksi = intent.getStringExtra("id_transaksi").toString()

//        detailTransaksiPresenter = DetailTransaksiPresenter(this)
//        detailTransaksiPresenter.getResponse(idTransaksi)

        binding!!.ivUpload.setOnClickListener {
            checkAndroid()
        }

        binding!!.btnKirim.setOnClickListener {
            if (bitmap != null) {
                dialogSetuju()
            }
        }

        binding!!.btnJenisPembayaran.setOnClickListener {
            dialogJenisPembayaran()
        }

    }

//    override fun onSuccessDetailTransaksi(payloadDetailTransaksi: PayloadDetailTransaksi) {
//        binding.tvHarga.text = payloadDetailTransaksi.bayar_hari
//        binding.tvNamaBankRental.text = "Bank "+payloadDetailTransaksi.nama_bank_rental+", "
//        binding.tvNorekRental.text = payloadDetailTransaksi.norek_rental
//        binding.etNama.setText(payloadDetailTransaksi.nama_rekening)
//        binding.etAtm.setText(payloadDetailTransaksi.norek)
//        binding.etNamaBank.setText(payloadDetailTransaksi.nama_bank)
//    }
//
//    override fun onErrorResponse() {
//
//    }

    private fun dialogJenisPembayaran () {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_jenis_pembayaran)

        dialog.show()
    }

    private fun checkAndroid() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 555
                )
            } catch (e: Exception) {
            }
        } else {
            pickImage()
//            selectImage(this)
        }
    }

//    private fun selectImage(context: Context) {
//        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
//        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//        builder.setTitle("Choose your profile picture")
//        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
//            when {
//                options[item] == "Take Photo" -> {
//                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(takePicture, 0)
//                }
//                options[item] == "Choose from Gallery" -> {
//                    val pickPhoto = Intent(
//                        Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                    )
//                    startActivityForResult(pickPhoto, 1) //one can be replaced with any action code
//                }
//                options[item] == "Cancel" -> {
//                    dialog.dismiss()
//                }
//            }
//        })
//        builder.show()
//    }

    private fun pickImage() {
        CropImage.startPickImageActivity(this)
    }

    private fun croprequest(imageUri: Uri) {
        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true)
            .start(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage()
//            selectImage(this)
        } else {
            //requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            checkAndroid()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("resultscods", requestCode.toString())
//        if (resultCode != RESULT_CANCELED) {
//            when (requestCode) {
//                0 -> if (resultCode == RESULT_OK && data != null) {
//                    bitmap = data.extras!!["data"] as Bitmap?
////                    binding!!.ivUpload.alpha = 1F
//                    binding.ivUpload.setImageBitmap(bitmap)
//                    val baos = ByteArrayOutputStream()
//                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 25, baos)
//                    val resizeBitmap = resize(bitmap!!, bitmap?.width!! / 2, bitmap?.height!! / 2);
////                    binding!!.thumb.visibility = View.GONE
//                    binding!!.ivUpload.visibility = View.VISIBLE
//                    binding!!.ivUpload.setImageBitmap(resizeBitmap)
//                }
//                1 -> if (resultCode == RESULT_OK && data != null) {
//                    val selectedImage = data.data
//                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                    if (selectedImage != null) {
//                        val cursor: Cursor? = contentResolver.query(
//                            selectedImage,
//                            filePathColumn, null, null, null
//                        )
//                        if (cursor != null) {
//                            cursor.moveToFirst()
//                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
//                            val picturePath: String = cursor.getString(columnIndex)
//
//                            bitmap = BitmapFactory.decodeFile(picturePath)
//                            val baos = ByteArrayOutputStream()
//                            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 25, baos)
//                            val resizeBitmap = resize(bitmap!!, bitmap?.width!! / 2, bitmap?.height!! / 2);
//
//                            binding.ivUpload.setImageBitmap(resizeBitmap)
//                            cursor.close()
//
////                            binding!!.thumb.visibility = View.GONE
//                            binding!!.ivUpload.visibility = View.VISIBLE
//                            binding!!.ivUpload.setImageBitmap(resizeBitmap)
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var image = image
        return if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            image
        } else {
            image
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri = CropImage.getPickImageResultUri(this, data)
            croprequest(imageUri)
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, result.uri)
                    val baos = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 25, baos)
//                    binding!!.ivUpload.visibility = View.GONE
//                    binding!!.ivPengaduan.visibility = View.VISIBLE
                    binding!!.ivUpload.alpha = 1F
                    binding!!.ivUpload.setImageBitmap(bitmap)

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun saveBitmap(ctx: Context, bmp: Bitmap): File? {
        val filesDir = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        if (!filesDir.exists()) {
            if (filesDir.mkdirs()) {
            }
        }
        val file = File(filesDir, "temp.png")
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw IOException("Cant able to create file")
                }
            }
            val os: OutputStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }

    private fun uploadImage(bitmapp: Bitmap) {
        val file: File? = saveBitmap(this@PembayaranActivity, bitmap!!)
        val filePart = MultipartBody.Part.createFormData(
            "foto", file!!.name, RequestBody.create(
                MediaType.parse("image/*"), file
            )
        )
        val idTransaksi: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            idTransaksi
        )
        val etNama: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "")
        val etNomor: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "")
        val api: Api = InitRetrofit().getInitInstance()
        val call: Call<Any> = api.uploadImage(filePart, idTransaksi, etNama, etNomor)
        call.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                Log.d("TagResponse2", response.toString())
                if (response.isSuccessful) {
                    val gson = Gson()
                    var jsonString = gson.toJson(response.body())
                    val obj = JSONObject(jsonString)
                    Log.d("TagResponse2", obj.toString())
                    var status = obj.get("status")
                    if (status as Boolean) {
                        Toast.makeText(
                            this@PembayaranActivity,
                            "Pembayaran berhasil",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@PembayaranActivity,
                            "Gagal, Terjadi Kesalahan",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        this@PembayaranActivity,
                        "Gagal, Terjadi Kesalahan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
//                Log.e("android1", "errornya ${error.t}")
                Toast.makeText(
                    this@PembayaranActivity,
                    "Error, Terjadi Kesalahan",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun dialogSetuju() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_pembayaran)

        val oke = dialog.findViewById<RelativeLayout>(R.id.btnOke)
        val batal = dialog.findViewById<RelativeLayout>(R.id.btnBatal)
        dialog.show()

        oke.setOnClickListener {
            buatJadwal().execute()
            dialog.dismiss()
        }

        batal.setOnClickListener {
            dialog.dismiss()
        }
    }

    inner class buatJadwal() : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            Log.d("Tag", "On doInBackground...")
            uploadImage(bitmap!!)
            pDialog.dismiss()
            val myIntent = Intent(applicationContext, MainActivityLogin::class.java)
            myIntent.putExtra("first", 1)
            startActivity(myIntent)
            finish()

            return "You are at PostExecute"
        }

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@PembayaranActivity)
            pDialog.setMessage("Mohon tunggu...")
            pDialog.isIndeterminate = false
            pDialog.setCancelable(true)
            pDialog.show()
        }
    }
}