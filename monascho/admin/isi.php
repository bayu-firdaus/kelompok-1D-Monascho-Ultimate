<?php
include "koneksi.php";

if ($_GET['module'] == "home") 
{
    include "module/home.php";
}
else if ($_GET['module'] == "dashboard") 
{
    include "module/dashboard/index.php";
}
else if ($_GET['module'] == "konsumen") 
{
    include "module/konsumen/index.php";
} 
else if ($_GET['module'] == "informasi") {
    include "module/informasi/index.php";
} 
else if ($_GET['module'] == "produk") {
    include "module/produk/index.php";
}
else if ($_GET['module'] == "transaksi") {
    include "module/transaksi/index.php";
} 
else if ($_GET['module'] == "laporan") {
    include "module/laporan/index.php";
}

    
?>