<?php
include "../../koneksi.php";
$module = $_GET['module'];
$aksi = $_GET['aksi'];

define('API_ACCESS_KEY', 'AAAA6UUIX14:APA91bGMN-Yx-db7wEDzWnEQG5sQtW-_Va8RqoOAvo-WDwGUPRLOnspRLl6nG2eBFl1yBLfViD4gQPehWo7zNTDvbVEMhZGey8i6Hnc1niDxwFY8wbjyPZwpmdAlECzLj9zPdSxwqCRR');

if ($module == 'transaksi' and $aksi == 'terima') {
    $transaksi = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='$_GET[id_transaksi]'"));
    $tokenNotif = mysqli_fetch_array(mysqli_query($konek, "select * from tb_konsumen where id_konsumen='$transaksi[id_konsumen]'"))["token"];
    $tb_item_transaksi = mysqli_query($konek, "SELECT * FROM tb_item_transaksi WHERE id_transaksi='$_GET[id_transaksi]'");
    $id_transaksi = $_GET["id_transaksi"];
    $status = $_GET["status"];
    $status_transaksi = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='$id_transaksi'"))["status"];

    if ($status_transaksi == "0") {
        if ($status == 4) {
            send("Pesanan Anda Sudah diterima", $tokenNotif);
            insert_lacakpesanan($id_transaksi, "Pesanan Anda Sudah diterima", "", 4);
            mysqli_query($konek, "UPDATE tb_transaksi SET status='4' WHERE id_transaksi='$id_transaksi'");
            while ($key = mysqli_fetch_array($tb_item_transaksi)) {
                update_stok_produk($key);
            }
        } else {
            insert_lacakpesanan($id_transaksi, "Pesanan Anda Sudah diterima, Selesaikan Pembayaran", "", 1);
            send("Pesanan Anda Sudah diterima, Selesaikan Pembayaran", $tokenNotif);
            mysqli_query($konek, "UPDATE tb_transaksi SET status='1' WHERE id_transaksi='$id_transaksi'");
        }
    } else if ($status_transaksi == "1") {
        mysqli_query($konek, "UPDATE tb_transaksi SET status='2' WHERE id_transaksi='$id_transaksi'");
    } else if ($status_transaksi == "2") {
        insert_lacakpesanan($id_transaksi, "Pembayaran Dikonfirmasi", "", 3);
        send("Pembayaran Dikonfirmasi", $tokenNotif);
        while ($key = mysqli_fetch_array($tb_item_transaksi)) {
            update_stok_produk($key);
        }
        mysqli_query($konek, "UPDATE tb_transaksi SET status='3' WHERE id_transaksi='$id_transaksi'");
    } else if ($status_transaksi == "3") {
        insert_lacakpesanan($id_transaksi, "Dalam Proses Pengemasan", "", 4);
        send("Dalam Proses Pengemasan", $tokenNotif);
        mysqli_query($konek, "UPDATE tb_transaksi SET status='4' WHERE id_transaksi='$id_transaksi'");
    } else if ($status_transaksi == "4") {
        insert_lacakpesanan($id_transaksi, "Pesanan anda sudah diantar Oleh " . $_GET["nama"], $_GET["nomor"], 5);
        send("Pesanan anda sudah diantar", $tokenNotif);
        mysqli_query($konek, "UPDATE tb_transaksi SET status='5' WHERE id_transaksi='$id_transaksi'");
    } else if ($status_transaksi == "5") {
        mysqli_query($konek, "UPDATE tb_transaksi SET status='6' WHERE id_transaksi='$id_transaksi'");
    } else if ($status_transaksi == "6") {
        insert_lacakpesanan($id_transaksi, "Selesai", "", 7);
        mysqli_query($konek, "UPDATE tb_transaksi SET status='7' WHERE id_transaksi='$id_transaksi'");
    }

    header('location:../../index.php?module=' . $module);
    
} else if ($module == 'produk' and $aksi == 'hapus') {

}

function update_stok_produk($key)
{
    include "../../koneksi.php";
    $stoks = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='$key[id_produk]'"));
    $stok = $stoks["stok"] - $key["kuantitas"];
    mysqli_query($konek, "UPDATE tb_produk SET stok='$stok' WHERE id='$key[id_produk]'");
}

function insert_lacakpesanan($idtransaksi, $pesan, $nomor, $jenis)
{
    include "../../koneksi.php";
    date_default_timezone_set('Asia/Makassar');

    $tgl = date('Y-m-d');
    $jam = date('H:i:s');
    $sql = "INSERT INTO tb_lacakpesanan 
    (
    id_transaksi,tgl,jam,pesan,nomor,jenis) VALUES (
    '$idtransaksi','$tgl','$jam','$pesan','$nomor','$jenis'
    )";
    mysqli_query($konek, $sql);
}

function send($body, $tokenNotif)
{
    $msg = array(
        'title' => "Monascho Ultimate",
        'body' => $body,
        'ids' => "transaksi"
    );

    $fields = array(
        'to' => $tokenNotif,
        'data' => $msg,
        'channel' => 'KONSUMEN_NOTIF_APPS', 'priority' => 'high'
    );

    $headers = array('Authorization: key=' . API_ACCESS_KEY, 'Content-Type: application/json');
    //Using curl to perform http request 
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    //Getting the result 
    $result = curl_exec($ch);
    curl_close($ch);
    //Decoding json from result 
    $res = json_decode($result);
    var_dump($res);
    return $res;
}
