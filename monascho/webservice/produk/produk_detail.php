<?php
error_reporting(0);
include '../koneksi.php';

$id = array_pop($uriSegments);
$query = "SELECT * FROM tb_produk where id='$id'";
$obj_query = mysqli_query($konek, $query);
$data = mysqli_fetch_assoc($obj_query);

$harga_diskon = (int)$data['harga'] - (($data['diskon'] / 100) * (int)$data['harga']);
$dataar = array(
    'id' => $data['id'],
    'nama' => $data['nama'],
    'harga' => round(((int)$data['diskon'] > 0) ? $harga_diskon . "" : $data['harga']),
    'diskon' => $data['diskon'],
    'foto' => $data["foto"],
    'foto2' => $data["foto2"],
    'foto3' => $data["foto3"],
    'foto4' => $data["foto4"],
    'foto5' => $data["foto5"],
    'status' => $data["status"],
    'stok' => $data["stok"],
    'deskripsi' => $data["deskripsi"],
);

if ($data) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'payload' => $dataar
        )
    );
} else {
    echo json_encode(
        array(
            'status' => false,
            'message' => 'gagal menampilkan data',
            'payload' => null
        )
    );
}

header('Content-Type: application/json');
