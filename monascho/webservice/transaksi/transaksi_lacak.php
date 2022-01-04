<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$id_transaksi = array_pop($uriSegments);

$data = $crud->select_from('tb_lacakpesanan WHERE id_transaksi="' . $id_transaksi . '" ORDER BY jenis desc')->fetchAll();
$arr = array();
foreach ($data as $row) {
    $dataar[] = array(
        'id_transaksi' => $row['id_transaksi'],
        'tgl' => tgl_indo($row['tgl']),
        'jam' => substr($row['jam'], 0, 5),
        'pesan' => $row['pesan'],
        'nomor' => $row['nomor'],
        'jenis' => $row['jenis'],
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
