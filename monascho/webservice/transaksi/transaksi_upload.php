<?php
error_reporting(0);
define("LOKASI_DOKUMEN", '../fbukti/');
include '../koneksi.php';
require '../Query.php';

$crud = new Crud($koneksi);

$filename = $_FILES["foto"]["name"];
if ($filename) {
    $foto = _uploadImagep();
} else {
    $foto = "";
}

$params = array(
    'foto' => $foto,
    'nama_pengirim' => $_POST["nama_pengirim"],
    'nomor_pengirim' => $_POST["nomor_pengirim"],
    'status' => 2
);

mysqli_query($konek, "UPDATE tb_transaksi SET 
foto = '$foto',
nama_pengirim = '$_POST[nama_pengirim]',
nomor_pengirim = '$_POST[nomor_pengirim]',
status = 2
WHERE id_transaksi='$_POST[id_transaksi]'");

date_default_timezone_set('Asia/Makassar');
$crud->insert('tb_lacakpesanan', array(
    'id_transaksi' => $_POST["id_transaksi"],
    'tgl' => date('Y-m-d'),
    'jam' => date('H:i:s'),
    'pesan' => "Menunggu Konfirmasi Pembayaran",
    'nomor' => "",
    'jenis' => 2,
));

if ($params) {
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
            'payload' => null
        )
    );
}

header('Content-Type: application/json');

function _uploadImagep()
{
    $filename = $_FILES['foto']['name'];

    if (!empty($filename)) {
        $extensionList = array("jpg", "png", "jpeg");
        $sliceName = explode(".", $filename);
        if (in_array($sliceName[1], $extensionList)) {
            $id_user = str_replace("-", "", uuid());
            $params['FILE_BERKAS'] = $id_user . '.' . 'jpg';
            upload_dokumen_file($params['FILE_BERKAS'], LOKASI_DOKUMEN, "foto");
            return $params['FILE_BERKAS'];
        } else {
            return "notpdf";
        }
    }
}

function upload_dokumen_file($fupload_name, $lokasi, $filename)
{
    $vfile_upload = $lokasi . $fupload_name;
    move_uploaded_file($_FILES[$filename]["tmp_name"], $vfile_upload);
}