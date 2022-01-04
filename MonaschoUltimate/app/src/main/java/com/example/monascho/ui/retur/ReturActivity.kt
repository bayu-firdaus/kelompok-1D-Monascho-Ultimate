package com.example.monascho.ui.retur

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.monascho.MainActivityLogin
import com.example.monascho.databinding.ActivityReturBinding
import com.example.monascho.network.Api
import com.example.monascho.network.InitRetrofit
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class ReturActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReturBinding
    private var idItem = ""
    private var kuantitas = 0
    private var bitmap: Bitmap? = null
    private lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        idItem = intent.getStringExtra("id_item").toString()
        kuantitas = intent.getStringExtra("kuantitas")!!.toInt()
        binding.etAtm.hint = "Jumlah Barang diretur maksimal $kuantitas"

        binding!!.ivUpload.setOnClickListener {
            checkAndroid()
        }

        binding!!.btnKirim.setOnClickListener {
            if (bitmap != null) {
//                dialogSetuju()
                if (binding.etAtm.text.toString().toInt() > kuantitas) {
                    Toast.makeText(this, "Melebihi batas yang dipesan", Toast.LENGTH_LONG).show()
                }else {
                    buatJadwal().execute()
                }
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
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
//            pickImage()
            selectImage(this)
        }
    }

    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                }
                options[item] == "Choose from Gallery" -> {
                    val pickPhoto = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1) //one can be replaced with any action code
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            pickImage()
            selectImage(this)
        } else {
            //requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            checkAndroid()
        }
    }

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
        Log.d("resultscods", requestCode.toString())
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
                    bitmap = data.extras!!["data"] as Bitmap?
//                    binding!!.ivUpload.alpha = 1F
                    binding.ivUpload.setImageBitmap(bitmap)
                    val baos = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 25, baos)
                    val resizeBitmap = resize(bitmap!!, bitmap?.width!! / 2, bitmap?.height!! / 2);
//                    binding!!.thumb.visibility = View.GONE
                    binding!!.ivUpload.visibility = View.VISIBLE
                    binding!!.ivUpload.setImageBitmap(resizeBitmap)
                }
                1 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = contentResolver.query(
                                selectedImage,
                                filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath: String = cursor.getString(columnIndex)

                            bitmap = BitmapFactory.decodeFile(picturePath)
                            val baos = ByteArrayOutputStream()
                            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 25, baos)
                            val resizeBitmap = resize(bitmap!!, bitmap?.width!! / 2, bitmap?.height!! / 2);

                            binding.ivUpload.setImageBitmap(resizeBitmap)
                            cursor.close()

//                            binding!!.thumb.visibility = View.GONE
                            binding!!.ivUpload.visibility = View.VISIBLE
                            binding!!.ivUpload.setImageBitmap(resizeBitmap)
                        }
                    }
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
        val file: File? = saveBitmap(this@ReturActivity, bitmap!!)
        val filePart = MultipartBody.Part.createFormData(
                "foto", file!!.name, RequestBody.create(
                MediaType.parse("image/*"), file
        )
        )
        val idItem: RequestBody = RequestBody.create(MediaType.parse("text/plain"), idItem)
        val kuantitasItem: RequestBody = RequestBody.create(MediaType.parse("text/plain"), binding.etAtm.text.toString())
        val alasan: RequestBody = RequestBody.create(MediaType.parse("text/plain"), binding.etNama.text.toString())
        val api: Api = InitRetrofit().getInitInstance()
        val call: Call<Any> = api.uploadRetur(filePart, idItem, alasan, kuantitasItem)
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
                                this@ReturActivity,
                                "berhasil",
                                Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                                this@ReturActivity,
                                "Gagal, Terjadi Kesalahan",
                                Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    Toast.makeText(
                            this@ReturActivity,
                            "Gagal, Terjadi Kesalahan",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
//                Log.e("android1", "errornya ${error.t}")
                Toast.makeText(
                        this@ReturActivity,
                        "Error, Terjadi Kesalahan",
                        Toast.LENGTH_LONG
                ).show()
            }
        })
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
            pDialog = ProgressDialog(this@ReturActivity)
            pDialog.setMessage("Mohon tunggu...")
            pDialog.isIndeterminate = false
            pDialog.setCancelable(true)
            pDialog.show()
        }
    }
}