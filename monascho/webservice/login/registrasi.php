<?php
error_reporting(0);
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

if (check_sudah_regis($_POST['no_telp'], $konek)) {
	$regis = false;
} else {
	$regis = true;
	$params = array(
		'nama' => $_POST['nama'],
		'no_telp' => $_POST['no_telp'],
		'tgl_create' => date('Y-m-d H:i:s'),
		'password' => $_POST['pass'],
		'alamat' => $_POST['alamat'],
		'tgl_lahir' => $_POST['tgl_lahir'],
		'jk' => $_POST['jk'],
		'email' => $_POST['email']
	);
	$crud->insert('tb_konsumen', $params);
}

if ($regis) {
	echo json_encode(
		array(
			'status' => true,
			'message' => 'sukses menampilkan data',
			'payload' => "sukses"
		)
	);
} else {
	echo json_encode(
		array(
			'status' => false,
			'message' => 'gagal menampilkan data',
			'payload' => null
		)
	);
}

header('Content-Type: application/json');

function check_sudah_regis($nomor, $konek)
{
	$query = "SELECT * FROM tb_konsumen where no_telp='$nomor'";
	$obj_query = mysqli_query($konek, $query);
	if (mysqli_num_rows($obj_query)) {
		return true;
	} else {
		return false;
	}
}