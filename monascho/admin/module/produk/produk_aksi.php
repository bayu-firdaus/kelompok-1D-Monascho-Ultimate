<?php
include "../../koneksi.php";
$module = $_GET['module'];
$aksi = $_GET['aksi'];

define("LOKASI_DOKUMEN", '../../public/img/produk/');

if ($module == 'produk' and $aksi == 'tambah') {
    $f1 = $_FILES["foto1"]["name"];
    $f2 = $_FILES["foto2"]["name"];
    $f3 = $_FILES["foto3"]["name"];
    $f4 = $_FILES["foto4"]["name"];
    $f5 = $_FILES["foto5"]["name"];
    if ($f1) { $foto1 = _uploadImagep("foto1"); } else { $foto1 = "";}
    if ($f2) { $foto2 = _uploadImagep("foto2"); } else { $foto2 = "";}
    if ($f3) { $foto3 = _uploadImagep("foto3"); } else { $foto3 = "";}
    if ($f4) { $foto4 = _uploadImagep("foto4"); } else { $foto4 = "";}
    if ($f5) { $foto5 = _uploadImagep("foto5"); } else { $foto5 = "";}

    $nama = $_POST['nama'];
    $harga = str_replace(array('Rp', '.', ' '), '', $_POST['harga']);
    $stok = $_POST['stok'];
    $diskon = $_POST['diskon'];
    $deskripsi = $_POST['deskripsi'];

    $sql = "INSERT INTO tb_produk 
    (
    nama,harga,stok,diskon,deskripsi,foto,foto2,foto3,foto4,foto5) VALUES (
    '$nama','$harga','$stok','$diskon','$deskripsi','$foto1','$foto2','$foto3','$foto4','$foto5'
    )";
    mysqli_query($konek, $sql);
    header('location:../../index.php?module=' . $module);

} else if ($module == 'produk' and $aksi == 'hapus') {

    $hasil = mysqli_query($konek, "SELECT * FROM tb_item_transaksi where id_produk='" . $_GET['id'] . "'");
    while ($key = mysqli_fetch_array($hasil)) {
        mysqli_query($konek, "DELETE FROM tb_transaksi WHERE id_transaksi='" . $_GET['id'] . "'");
    }
    mysqli_query($konek, "DELETE FROM tb_item_transaksi WHERE id_produk='" . $_GET['id'] . "'");
    mysqli_query($konek, "DELETE FROM tb_keranjang WHERE id_produk='" . $_GET['id'] . "'");
    mysqli_query($konek, "DELETE FROM tb_produk WHERE id='" . $_GET['id'] . "'");
    echo "<script>window.location='javascript:history.go(-1)';</script>";
}

else if ($module == 'produk' and $aksi == 'edit') {

    $nama = $_POST['nama'];
    $harga = str_replace(array('Rp', '.', ' '), '', $_POST['harga']);
    $stok = $_POST['stok'];
    $diskon = $_POST['diskon'];
    $deskripsi = $_POST['deskripsi'];
    
    $query = mysqli_query($konek, "UPDATE tb_produk SET 
    nama='$nama',
    harga='$harga',
    stok='$stok',
    diskon='$diskon',
    deskripsi='$deskripsi' 
    WHERE id='$_GET[id_produk]'");
    header('location:../../index.php?module='.$module);
} 
else if ($module == 'produk' and $aksi == 'editgambar') {

    $nama_foto = $_POST['nama_foto'];
    $f = $_FILES["foto"]["name"];
    if ($f) {
        $foto = _uploadImagep("foto");
    } else {
        $foto = $_POST['fotonama'];
    }

    $query = mysqli_query($konek, "UPDATE tb_produk SET $_POST[fotoke] ='$foto' WHERE id='$_GET[id_produk]'");

    header('location:../../index.php?module=' . $module);
}

function _uploadImagep($name)
{
    $filename = $_FILES[$name]['name'];

    if (!empty($filename)) {
        $extensionList = array("jpg", "png", "jpeg");
        $sliceName = explode(".", $filename);
        if (in_array($sliceName[1], $extensionList)) {
            $id_user = str_replace("-", "", uuid());
            $params['FILE_BERKAS'] = $id_user . '.' . 'jpg';
            upload_dokumen_file($params['FILE_BERKAS'], LOKASI_DOKUMEN, $name);
            return $params['FILE_BERKAS'];
        } else {
            return "";
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
