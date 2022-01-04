<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$Date = date('Y-m-d H:i:s');
$id_transaksi = str_replace("-", "", uuid());
$jarak = convert(hitungJarak($_POST['lat'], $_POST['lng'], "-8.180233123512686", "113.688988"));
$arr = array();
for ($i = 0; $i < count($_POST['id_produk']); $i++) {
    $produk = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $_POST['id_produk'][$i] . "'"));
    $harga_diskon = (int) $produk["harga"] - (($produk["diskon"] / 100) * (int) $produk["harga"]);
    $total = $harga_diskon * $_POST["kuantitas"][$i];
    array_push($arr, $total);
    $total_harga = array_sum($arr);

    $paramsitem = array(
        'id_transaksi' => $id_transaksi,
        'id_konsumen' => $_POST['id_konsumen'],
        'id_produk' => $_POST['id_produk'][$i],
        'kuantitas' => $_POST['kuantitas'][$i]
    );
    $crud->insert('tb_item_transaksi', $paramsitem);
}

$params = array(
    'id_transaksi' => $id_transaksi,
    'id_konsumen' => $_POST['id_konsumen'],
    'lat' => $_POST['lat'],
    'lng' => $_POST['lng'],
    'tgl_transaksi' => $Date,
    'pembayaran' => $_POST['pembayaran'],
    'alamat' => $_POST['alamat'],
    'subtotal' => $total_harga,
    'ongkir' => $jarak * 100,
    'total' => $total_harga + ($jarak * 100)
);

$crud->insert('tb_transaksi', $params);

if($params) {
    for ($j = 0; $j < count($_POST['id_keranjang']); $j++) {
        mysqli_query($konek, "DELETE FROM tb_keranjang WHERE id_keranjang='" . $_POST['id_keranjang'][$j] . "'");
    }
    echo json_encode(
        array(
            'status' => true,
            'message' => 'sukses menampilkan data',
            'payload' => "sukses"
        )
    );
}else{
    echo json_encode(
        array(
            'status' => false,
            'message' => 'gagal menampilkan data',
            'payload' => "gagal"
        )
    );
}

header('Content-Type: application/json');

function convert($value)
{
    return $value;
}

function hitungJarak($lokasi1_lat, $lokasi1_long, $lokasi2_lat, $lokasi2_long, $unit = 'km', $desimal = 2)
{
    $derajat = rad2deg(acos((sin(deg2rad($lokasi1_lat)) * sin(deg2rad($lokasi2_lat))) + (cos(deg2rad($lokasi1_lat)) * cos(deg2rad($lokasi2_lat)) * cos(deg2rad($lokasi1_long - $lokasi2_long)))));

    switch ($unit) {
        case 'km':
            $jarak = $derajat * 111.13384;
            break;
        case 'mi':
            $jarak = $derajat * 69.05482;
            break;
        case 'nmi':
            $jarak =  $derajat * 59.97662;
    }
    return round($jarak, $desimal);
}