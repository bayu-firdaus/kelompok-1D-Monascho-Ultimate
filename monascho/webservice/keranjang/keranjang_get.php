<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$id_konsumen = array_pop($uriSegments);

$data = $crud->select_from('tb_keranjang WHERE id_konsumen="'.$id_konsumen.'" ORDER BY id_keranjang desc')->fetchAll();
$arr = array();
foreach ($data as $row) {
    $produk = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $row['id_produk'] . "'"));

    $harga_diskon = (int) $produk["harga"] - (($produk["diskon"] / 100) * (int) $produk["harga"]);
    $subTotal =  $harga_diskon * $row['kuantitas_item'];
    array_push($arr, $subTotal);
    $total_harga = array_sum($arr);

    $dataar[] = array(
        'id_keranjang' => $row['id_keranjang'],
        'id_produk' => $row['id_produk'],
        'nama_produk' => $produk["nama"],
        'harga' => round($harga_diskon) . "",
        'foto' => $produk["foto"],
        'kuantitas_item' => $row['kuantitas_item'],
        'subTotal' => round($subTotal) . ""
    );
}

if ($dataar) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'total' => round($total_harga) . "",
            'payload' => $dataar
        )
    );
} else {
    echo json_encode(
        array(
            'status' => false,
            'message' => 'gagal menampilkan data',
            'total' => "",
            'payload' => null
        )
    );
}

header('Content-Type: application/json');