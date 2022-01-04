<?php

date_default_timezone_set("Asia/Jakarta");

function getRupiah($harga = 0)
{
    if ($harga != null) {
        return "Rp " . number_format($harga, 0, ",", ".");
    } else {
        return "Rp 0";
    }
}

function getPembayaran($sts)
{
    if ($sts == "1") {
        $ret = "Transfaer";
    } else {
        $ret = "COD";
    }
    return $ret;
}

function getStatus($status)
{
    if ($status == "0") {
        $st = "<span class='text-danger font-weight-700'>Menunggu Konfirmasi</span>";
    } else if ($status == "1") {
        $st = "<span class='text-info font-weight-700'>Menunggu Pembayaran</span>";
    } else if ($status == "2") {
        $st = "<span class='text-primary font-weight-700'>Menunggu Konfirmasi Pembayaran</span>";
    } else if ($status == "3") {
        $st = "<span class='text-success font-weight-700'>Pembayaran DiKonfrimasi</span>";
    } else if ($status == "4") {
        $st = "<span class='text-primary font-weight-700'>Dalam Proses Pengemesan</span>";
    } else if ($status == "5") {
        $st = "<span class='text-primary font-weight-700'>Pesanan Diantar</span>";
    } else if ($status == "6") {
        $st = "<span class='text-success font-weight-700'>Pesanan Sudah Diterima</span>";
    }

    return $st;
}

?>