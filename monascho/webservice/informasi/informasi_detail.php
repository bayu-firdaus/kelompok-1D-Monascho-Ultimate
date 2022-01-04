<?php
error_reporting(0);
include '../koneksi.php';

$id = array_pop($uriSegments);
$query = "SELECT * FROM tb_informasi where id='$id'";
$obj_query = mysqli_query($konek, $query);
$data = mysqli_fetch_assoc($obj_query);

if ($data) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'payload' => $data
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
