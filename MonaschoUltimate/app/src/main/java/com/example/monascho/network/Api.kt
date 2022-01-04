package com.example.monascho.network

import com.example.monascho.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("Login/login_konsumen")
    fun login(
        @Field("user") user: String,
        @Field("pass") pass: String,
        @Field("token") token: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("Login/registrasi")
    fun regis(
        @Field("no_telp") no_telp: String,
        @Field("nama") nama: String,
//        @Field("kecamatan") kecamatan: String,
        @Field("alamat") alamat: String,
//        @Field("kodepos") kodepos: String,
        @Field("tgl_lahir") tgl_lahir: String,
        @Field("jk") jk: String,
        @Field("email") email: String,
        @Field("pass") pass: String,
    ): Call<ResponseSignUp>

    @GET("Informasi/informasi")
    fun getInformasi(
    ): Call<ResponseInformasi>

    @GET("Informasi/informasi_detail//{id}")
    fun getInformasiDetail(
        @Path("id") id: String?
    ): Call<ResponseDetailInformasi>

    @GET("produk_filter.php")
    fun getProduk(
        @Query("q") nama: String?
    ): Call<ResponseProduk>

    @GET("Produk/produk_detail/{id}")
    fun getProdukDetail(
            @Path("id") id: String?
    ): Call<ResponseProdukDetail>

    @FormUrlEncoded
    @POST("Keranjang/keranjang")
    fun postKeranjang(
            @Field("id_konsumen") id_konsumen: String,
            @Field("id_produk") id_produk: String,
            @Field("kuantitas_item") kuantitas_item: String
    ): Call<ResponsePostKeranjang>

    @GET("Keranjang/{id_konsumen}")
    fun getKeranjang(
            @Path("id_konsumen") id_konsumen: String?
    ): Call<ResponseKeranjang>

    @FormUrlEncoded
    @POST("Keranjang/update_keranjang")
    fun updateKeranjang(
            @Field("id_keranjang") update_keranjang: String,
            @Field("kuantitas_item") kuantitas_item: String
    ): Call<ResponsePostKeranjang>

    @FormUrlEncoded
    @POST("Transaksis/transaksi")
    fun postTransaksi(
        @Field("id_konsumen") id_konsumen: String,
        @Field("id_produk[]") id_produk : ArrayList<String>,
        @Field("kuantitas[]") kuantitas_item:  ArrayList<String>,
        @Field("id_keranjang[]") id_keranjang:  ArrayList<String>,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("pembayaran") pembayaran: String,
        @Field("alamat") alamat: String
    ): Call<ResponsePostKeranjang>

    @GET("Transaksi/{id_konsumen}")
    fun getRiwayatTransaksi(
            @Path("id_konsumen") idKonsumen: String?
    ): Call<ResponseRiwayatTransaksi>

    @GET("Transaksi/detail/{id_transaksi}")
    fun getRiwayatTransaksiDetail(
        @Path("id_transaksi") idTransaksi: String?
    ): Call<ResponseRiwayatTransaksiDetail>

    @Multipart
    @POST("Transaksis/uploadBukti")
    fun uploadImage(
        @Part file: MultipartBody.Part?,
        @Part("id_transaksi") idTransaksi: RequestBody?,
        @Part("nama_pengirim") namaPengirim: RequestBody?,
        @Part("nomor_pengirim") nomorPengirim: RequestBody?
    ): Call<Any>

    @Multipart
    @POST("Transaksi/retur")
    fun uploadRetur(
            @Part file: MultipartBody.Part?,
            @Part("id_item") idItem: RequestBody?,
            @Part("alasan") alasan: RequestBody?,
            @Part("kuantitas_item") kuantitas_item: RequestBody?
    ): Call<Any>

    @FormUrlEncoded
    @POST("Transaksis/konfirmasi_sampai")
    fun postSampai(
            @Field("id_transaksi") idTransaksi: String
    ): Call<ResponsePostKeranjang>

    @GET("profil/profil/{id_konsumen}")
    fun getProfil(
            @Path("id_konsumen") id_konsumen: String?
    ): Call<ResponseProfil>

    @FormUrlEncoded
    @POST("profil/update_profil")
    fun updateProfil(
            @Field("id_konsumen") id_konsumen: String,
            @Field("nama") nama: String,
            @Field("kecamatan") kecamatan: String,
            @Field("alamat") alamat: String,
            @Field("kodepos") kodepos: String,
            @Field("tgl_lahir") tgl_lahir: String,
            @Field("jk") jk: String,
            @Field("email") email: String,
    ): Call<ResponsePostKeranjang>

    @GET("Transaksis/lacakpesanan/{id_transaksi}")
    fun getLacakPesanan(
            @Path("id_transaksi") id_transaksi: String?
    ): Call<ResponseLacakPesanan>

    @FormUrlEncoded
    @POST("Transaksis/add_rating")
    fun postRating(
        @Field("id_transaksi") id_transaksi: String,
        @Field("rating") rating: String,
        @Field("komentar") komentar: String
    ): Call<ResponsePostRatting>
}