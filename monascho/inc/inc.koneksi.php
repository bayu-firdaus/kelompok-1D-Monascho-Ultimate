<?php
include "koneksi_db.php";

$konek = mysqli_connect($server, $username, $password, $database);

if (mysqli_connect_errno()) {
	echo "gagal konek ke database" . mysqli_connect_errno();
}
