<?php
// error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$id_transaksi = array_pop($uriSegments);
$data = $crud->select_from('tb_item_transaksi WHERE id_transaksi="' . $id_transaksi . '" ORDER BY id_item asc')->fetchAll();
$statusTransaksi = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='" . $id_transaksi . "'"))["status"];

$arr = array();
foreach ($data as $row) {
    $produk = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $row['id_produk'] . "'"));
    $harga_diskon = (int) $produk["harga"] - (($produk["diskon"] / 100) * (int) $produk["harga"]);
    $total = $harga_diskon * $row["kuantitas"];
    array_push($arr, $total);
    $total_harga = array_sum($arr);
    
    $dataar[] = array(
        'id_item' => $row['id_item'],
        'id_produk' => $row['id_produk'],
        'nama_produk' => $produk["nama"],
        'harga' => getRupiah(round($harga_diskon)),
        'kuantitas' => $row["kuantitas"],
        'status_transaksi' => $statusTransaksi,
        'foto' => $produk["foto"],
        'total' => getRupiah(round($total)) . "",
        'status' => $row["status"]
    );
}

$transaksi = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='" . $id_transaksi . "'"));

if ($dataar) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'subtotal' => getRupiah($total_harga),
            'ongkir' => getRupiah($transaksi["ongkir"]),
            'total' => getRupiah($total_harga + $transaksi["ongkir"] . ""),
            'payload' => $dataar,
        )
    );
} else {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'gagal menampilkan data',
            'subtotal' => "",
            'ongkir' => "",
            'total' => "",
            'payload' => null
        )
    );
}

header('Content-Type: application/json');
