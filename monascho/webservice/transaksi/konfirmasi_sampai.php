<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

mysqli_query($konek, "UPDATE tb_transaksi SET status ='6' WHERE id_transaksi='$_POST[id_transaksi]'");
$crud->insert('tb_lacakpesanan', array(
    'id_transaksi' => $_POST["id_transaksi"],
    'tgl' => date('Y-m-d'),
    'jam' => date('H:i:s'),
    'pesan' => "Pesanan Sudah Diterima",
    'nomor' => "",
    'jenis' => 6,
));

if ($_POST["id_transaksi"]) {
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
