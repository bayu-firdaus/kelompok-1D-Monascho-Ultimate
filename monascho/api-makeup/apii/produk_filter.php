<?php
error_reporting(0);
include '../../webservice/koneksi.php';
require '../../webservice/Query.php';

$crud = new Crud($koneksi);

$q = $_GET['q'];
$new = str_replace('%20', ' ', $q);
if (empty($q)) {
    $like = "";
} else if ($q === null) {
    $like = "WHERE nama LIKE '%$new%'";
} else {
    $like = "WHERE nama LIKE '%$new%'";
}

$sql = "SELECT * FROM tb_produk " . $like;
$hasil = mysqli_query($konek, $sql . "ORDER BY id desc");

while ($row = mysqli_fetch_array($hasil)) {
    $harga_diskon = (int)$row['harga'] - (($row['diskon'] / 100) * (int)$row['harga']);
    $dataar[] = array(
        'id' => $row['id'],
        'nama' => $row['nama'],
        'harga' => ((int) $row['diskon'] > 0) ? getRupiah($harga_diskon) : getRupiah($row['harga']),
        'diskon' => $row['diskon'],
        'foto' => $row["foto"],
        'status' => $row["status"],
        'stok' => $row["stok"],
        'deskripsi' => $row["deskripsi"],
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
