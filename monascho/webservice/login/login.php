<?php
error_reporting(0);
include '../koneksi.php';
$user = $_POST['user'];
$pass = $_POST['pass'];
$token = $_POST['token'];

$query = "SELECT * FROM tb_konsumen where no_telp='$user' AND password='$pass'";
$obj_query = mysqli_query($konek, $query);
$login = mysqli_fetch_assoc($obj_query);

if ($login) {
	mysqli_query($konek, "UPDATE tb_konsumen SET token='$token' WHERE no_telp='$user'");

	echo json_encode(
		array(
			'status' => true,
			'message' => 'sukses menampilkan data',
			'payload' => $login
		)
	);
}else{
	echo json_encode(
		array(
			'status' => false,
			'message' => 'gagal menampilkan data',
			'payload' => null
		)
	);
}

header('Content-Type: application/json');

