<?php
include '../../inc/inc.library.php';
include '../../koneksi.php';

error_reporting(0);
ob_start();
if (isset($_POST['tgl_dari'])) {
    $tgl_dari = $_POST['tgl_dari'];
    $tgld = "AND tgl_transaksi >= '$tgl_dari'";
} else {
    $tgld = "";
    $tgl_dari = '';
}

if (isset($_POST['tgl_sampai'])) {
    $tgl_sampai = $_POST['tgl_sampai'];
    $tgls = " AND tgl_transaksi <= '$tgl_sampai'";
} else {
    $tgls = "";
    $tgl_sampai = '';
}

$sql = "SELECT * FROM tb_transaksi WHERE status = 7 " . $tgld . $tgls;
$hasil = mysqli_query($konek, $sql . "ORDER BY urutan desc");
?>
<html>

<head>
    <title>Data Gereja</title>
    <link href="../../public/print/report.css" rel="stylesheet" type="text/css">
    <style>
        .textx {
            mso-number-format: "\@";
        }

        td,
        th {
            font-size: 6.5pt;
            mso-number-format: "\@";
        }
    </style>
</head>

<body>
    <div id="container">
        <!-- Print Body -->
        <div id="body">
            <div style="height: 1px;" class="header" align="left">
                <h4> Laporan Penjualan <br>
                </h4>
            </div>
            <br>
            <table class="border thick">
                <thead>
                    <tr class="border thick">
                        <th>
                            <p style="font-size: 9px;">
                                #
                            </p>
                        </th>
                        <th style="width: 20%;">
                            <p style="font-size: 9px;">
                                Nama Konsumen
                            </p>
                        </th>
                        <th>
                            <p style="font-size: 9px;">
                                Rating & Komentar
                            </p>
                        </th>
                        <th>
                            <p style="font-size: 9px;">
                                <font size="2">Jumlah Produk</font>
                            </p>
                        </th>
                        <th style="width: 20%;">
                            <p style="font-size: 9px;">
                                <font size="2">Tanggal Transaksi</font>
                            </p>
                        </th>
                        <th>
                            <p style="font-size: 9px;">
                                <font size="2">Metode Pembayaran</font>
                            </p>
                        </th>
                        <th style="width: 18%;">
                            <p style="font-size: 9px;">
                                <font size="2">Total Harga</font>
                            </p>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $i = 1;

                    $arrTotal = array();
                    while ($p = mysqli_fetch_array($hasil)) {
                        $nama = mysqli_fetch_array(mysqli_query($konek, "select * from tb_konsumen where id_konsumen='" . $p['id_konsumen'] . "'"));
                        $jumlah = mysqli_fetch_array(mysqli_query($konek, "select count(*) as jumlah from tb_item_transaksi where id_transaksi='" . $p['id_transaksi'] . "'"));
                        array_push($arrTotal, $p["total"]);
                        $total_semua = array_sum($arrTotal);
                    ?>
                        <tr>
                            <td><?= $i++ ?></td>
                            <td>
                                <?= $nama['nama'] ?><br>
                                <p class="font-90 text-muted m-b-1"><?= $nama['no_telp'] ?></p>
                            </td>
                            <td><B><?= $p['rating'] ?></B>&nbsp;
                                <?php if ($p['rating'] == "") {
                                } else {
                                    echo '<img src="../../public/img/star.png" height="15">';
                                }
                                ?>
                                <br>
                                <?= $p['komentar'] ?>
                            </td>
                            <td><?= $jumlah["jumlah"] . " Produk"; ?></td>
                            <td><?= $p['tgl_transaksi'] ?></td>
                            <td><?= getPembayaran($p['pembayaran']) ?></td>
                            <td>
                                <?php
                                echo getRupiah($p["total"]) . "<br>";
                                ?>
                            </td>
                        </tr>
                    <?php } ?>
                </tbody>
            </table>

            <br><br><br>
            <table align="right">
                <tr>
                    <td width="280" align="center">
                        Vells Make Up
                        <br><br><br><br><br><br><br><br>
                        <B>Astrid</B>
                    </td>
                </tr>
            </table>

            <br><br><br>
        </div>
    </div>
</body>

</html>

<?php
$content = ob_get_clean();
require '../../public/print/html2pdf/autoload.php';
$pdf = new Spipu\Html2Pdf\Html2Pdf('P', 'A4', 'en');
$pdf->WriteHTML($content);
$pdf->Output('Laporan.pdf', 'I');
?>