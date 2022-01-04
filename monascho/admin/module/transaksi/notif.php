<?php
if (isset($_POST['view'])) {
    include "../../koneksi.php";
    $produks = mysqli_fetch_array(mysqli_query($konek, "select count(*) as allcount from tb_transaksi where status='0'"));

    $data = array(
        /*'notification' => $output,*/
        'unseen_notification'  => $produks['allcount'],
    );
    echo json_encode($data);
}