<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$params = array(
    'id_keranjang' => $_POST["id_keranjang"],
    'kuantitas_item' => $_POST["kuantitas_item"]
);

mysqli_query($konek, "UPDATE tb_keranjang SET kuantitas_item ='$_POST[kuantitas_item]' WHERE id_keranjang='$_POST[id_keranjang]'");

if ($params) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'payload' => "sukses"
        )
    );
} else {
    echo json_encode(
        array(
            'status' => false,
            'message' => 'gagal menampilkan data',
            'payload' => "gagal"
        )
    );
}

header('Content-Type: application/json');
