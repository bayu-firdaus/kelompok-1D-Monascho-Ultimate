<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

mysqli_query($konek, "UPDATE tb_transaksi SET 
rating ='$_POST[rating]',
komentar ='$_POST[komentar]'
WHERE id_transaksi='$_POST[id_transaksi]'");

if ($_POST['id_transaksi']) {
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
