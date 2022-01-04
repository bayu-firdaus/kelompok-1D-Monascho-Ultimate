package com.example.monascho.ui.pilihlokasi;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.monascho.MainActivityLogin;
import com.example.monascho.R;
import com.example.monascho.model.ResponsePostKeranjang;
import com.example.monascho.model.ResponseProfil;
import com.example.monascho.network.Api;
import com.example.monascho.network.InitRetrofit;
import com.example.monascho.utils.GPSTracker;
import com.example.monascho.utils.TmpData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihLokasiActivity extends FragmentActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private LatLng latLng, latLngusr;
    private GPSTracker gps;
    private double latuser, lonuser;
    private LatLng user;
    private Button btnKirim;
    private String idUser;
    private ProgressBar pb;
    private String pembayaran;
    private ImageView backBtn;
    private EditText etAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_lokasi);

        SharedPreferences pref = this.getSharedPreferences("login", MODE_PRIVATE);
        idUser = (pref.getString("code", null));

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            pembayaran = bundle.getString("pembayaran");
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnKirim = findViewById(R.id.btnKirim);
        pb = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);
        etAlamat = findViewById(R.id.etAlamat);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        getProfil();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        gps = new GPSTracker(PilihLokasiActivity.this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latuser = (gps.getLatitude());
            lonuser = (gps.getLongitude());
            user = new LatLng(latuser, lonuser);
            posisiuser();
        } else {
            gps.showSettingsAlert();
        }

        // TODO: Consider calling
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                latLng = mMap.getCameraPosition().target;
                googleMap.clear();
                try {
                    Log.e("Latitude", latLng.latitude + "");
                    Log.e("Longitude", latLng.longitude + "");
                    latLngusr = new LatLng(latLng.latitude, latLng.longitude);
//                    lattujuan = latLng.latitude;
//                    lngtujuan = latLng.longitude;
                    // start the animation
//                    imgmarker.startAnimation(AnimationUtils.loadAnimation(com.example.penjualan.ui.pilihlokasi.PilihLokasiActivity.this, R.anim.anim_marker_down));
//                    imgmarker_up.setAlpha(0.8f);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKirim.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                postTransaksi();
            }
        });

    }


    public void posisiuser() {
        mMap.addMarker(new MarkerOptions().position(user));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, 16));
    }

    private void postTransaksi () {
        Api api = new InitRetrofit().getInitInstance();
        Call<ResponsePostKeranjang> call = api.postTransaksi(idUser, TmpData.idProduk,TmpData.kuantitas,TmpData.idKeranjang, String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), pembayaran, etAlamat.getText().toString());
        call.enqueue(new Callback<ResponsePostKeranjang>() {
            @Override
            public void onResponse(Call<ResponsePostKeranjang> call, Response<ResponsePostKeranjang> response) {
                if (response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    TmpData.idProduk.clear();
                    TmpData.kuantitas.clear();
                    TmpData.idKeranjang.clear();

                    Intent myIntent = new Intent(getApplicationContext(), MainActivityLogin.class);
                    myIntent.putExtra("first", 1);
                    startActivity(myIntent);
                    finish();
//                    Toast.makeText(getApplicationContext(), "Berhasil, Segera Lakukan Pembayaran Di menu Riwayat Transaksi", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Pesanan Berhasil terkirim", Toast.LENGTH_SHORT).show();
                }else{
                    pb.setVisibility(View.GONE);
                    btnKirim.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostKeranjang> call, Throwable t) {

            }
        });
    }

    private void getProfil () {
        Api api = new InitRetrofit().getInitInstance();
        Call<ResponseProfil> call = api.getProfil(idUser);
        call.enqueue(new Callback<ResponseProfil>() {
            @Override
            public void onResponse(Call<ResponseProfil> call, Response<ResponseProfil> response) {
                if (response.isSuccessful()) {
                    etAlamat.setText(response.body().getPayload().getAlamat());
                }
            }

            @Override
            public void onFailure(Call<ResponseProfil> call, Throwable t) {

            }
        });
    }
}