<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

mysqli_query($konek, "UPDATE tb_konsumen SET 
nama = '$_POST[nama]',
kecamatan = '$_POST[kecamatan]',
alamat = '$_POST[alamat]',
kodepos = '$_POST[kodepos]',
tgl_lahir = '$_POST[tgl_lahir]',
jk = '$_POST[jk]',
email = '$_POST[email]'
WHERE id_konsumen='$_POST[id_konsumen]'");

if ($_POST['id_konsumen']) {
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
