<?php
include "../../koneksi.php";
$module = $_GET['module'];
$aksi = $_GET['aksi'];

define("LOKASI_DOKUMEN", '../../public/img/informasi/');

if ($module == 'informasi' and $aksi == 'tambah') {
    $filename = $_FILES["foto"]["name"];
    if ($filename) {
        $foto = _uploadImagep();
    } else {
        $foto = "";
    }
    
    $id = str_replace("-", "", uuid());
    $judul = $_POST['judul'];
    $ket = nl2br($_POST['ket']);
    $tgl = date('Y-m-d H:i:s');

    $sql = "INSERT INTO tb_informasi (id,judul,ket,tgl,foto) VALUES ('$id','$judul','$ket','$tgl','$foto')";
    $simpan = mysqli_query($konek, $sql);

    header('location:../../index.php?module='. $module);

} else if ($module == 'informasi' and $aksi == 'hapus') {
    $mySql = "DELETE FROM tb_informasi WHERE id='" . $_GET['id'] . "'";
    mysqli_query($konek, $mySql);
    echo "<script>window.location='javascript:history.go(-1)';</script>";
}

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

function uuid($trim = false)
{
    $format = ($trim == false) ? '%04x%04x-%04x-%04x-%04x-%04x%04x%04x' : '%04x%04x%04x%04x%04x%04x%04x%04x';
    return sprintf(
        $format,
        // 32 bits for "time_low"
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff),
        // 16 bits for "time_mid"
        mt_rand(0, 0xffff),
        // 16 bits for "time_hi_and_version",
        // four most significant bits holds version number 4
        mt_rand(0, 0x0fff) | 0x4000,
        // 16 bits, 8 bits for "clk_seq_hi_res",
        // 8 bits for "clk_seq_low",
        // two most significant bits holds zero and one for variant DCE1.1
        mt_rand(0, 0x3fff) | 0x8000,
        // 48 bits for "node"
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff)
    );
}