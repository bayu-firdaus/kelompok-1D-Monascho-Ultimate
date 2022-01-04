<?php
// include '../koneksi_db.php';
$server = "localhost";
$username = "root";
$password = "";
$database = "monascho";

$konek = mysqli_connect($server, $username, $password, $database);
if (mysqli_connect_errno()) {
	echo "gagal konek ke database" . mysqli_connect_errno();
}

