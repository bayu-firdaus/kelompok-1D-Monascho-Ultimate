<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$data = $crud->select_from('tb_informasi ORDER BY urutan desc')->fetchAll();
foreach ($data as $row) {
    $dataar[] = array(
        'urutan ' => $row['urutan'],
        'id' => $row['id'],
        'judul' => $row['judul'],
        'ket' => $row['ket'],
        'tgl' => $row['tgl'],
        'foto' => $row['foto']
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
            'status' => false,
            'message' => 'gagal menampilkan data',
            'payload' => null
        )
    );
}

header('Content-Type: application/json');
