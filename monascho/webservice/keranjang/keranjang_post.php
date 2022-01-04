<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$params = array(
    'id_konsumen' => $_POST['id_konsumen'],
    'id_produk' => $_POST['id_produk'],
    'kuantitas_item' => $_POST['kuantitas_item']
);

$jumlah_stok = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $_POST['id_produk'] . "'"))["stok"];

if (check_produk($_POST['id_konsumen'], $_POST['id_produk'], $konek)) {
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'payload' => "sukses"
        )
    );
} else {
    if($params){
        if ($jumlah_stok < $_POST['kuantitas_item']) {
            echo json_encode(
                array(
                    'status' => true,
                    'message' => 'sukses menampilkan data',
                    'payload' => "habis"
                )
            );
        }else{
            $crud->insert('tb_keranjang', $params);
            echo json_encode(
                array(
                    'status' => true,
                    'message' => 'sukses menampilkan data',
                    'payload' => "sukses"
                )
            );
        }
    }else{
        echo json_encode(
            array(
                'status' => false,
                'message' => 'gagal menampilkan data',
                'payload' => "gagal"
            )
        );
    }
}

header('Content-Type: application/json');

function check_produk($id_konsumen, $id_produk, $konek)
{
    $query = "SELECT * FROM tb_keranjang where id_konsumen='$id_konsumen' AND id_produk='$id_produk'";
    $obj_query = mysqli_query($konek, $query);
    if (mysqli_num_rows($obj_query)) {
        return true;
    } else {
        return false;
    }
}
