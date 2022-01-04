<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$id_konsumen = array_pop($uriSegments);

$data = $crud->select_from('tb_transaksi WHERE id_konsumen="' . $id_konsumen . '" ORDER BY urutan desc')->fetchAll();
$arr = array();
foreach ($data as $row) {
    $jumlah = mysqli_fetch_array(mysqli_query($konek, "select count(*) as jumlah from tb_item_transaksi where id_transaksi='" . $row['id_transaksi'] . "'"));
    $dataar[] = array(
        'id_transaksi' => $row['id_transaksi'],
        'tgl_transaksi' => $row['tgl_transaksi'],
        'id_konsumen' => $row['id_konsumen'],
        'jumlah_transaksi' => $jumlah["jumlah"],
        'status' => $row['status'],
        'total' => getRupiah(round($row['total']))
    );
}

if ($dataar) {
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
            'status' => true,
            'message' => 'gagal menampilkan data',
            'payload' => null
        )
    );
}

header('Content-Type: application/json');
