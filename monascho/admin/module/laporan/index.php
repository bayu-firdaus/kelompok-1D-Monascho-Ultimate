<?php
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
<div class="page-wrapper">
    <!-- ============================================================== -->
    <!-- Bread crumb and right sidebar toggle -->
    <!-- ============================================================== -->
    <div class="page-breadcrumb">
        <div class="row">
            <div class="col-12 d-flex no-block align-items-center">
                <h4 class="page-title">Laporan Penjualan</h4>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <form action="?module=laporan" method="post">
            <div class="row">
                <div class="col-md-2">
                    <div class="card">
                        <input placeholder="Dari Tanggal" name="tgl_dari" class="form-control" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" id="date" value="<?php echo $tgl_dari ?>" required>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="card">
                        <input placeholder="Sampai Tanggal" name="tgl_sampai" class="form-control" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" id="date" value="<?php echo $tgl_sampai ?>" required>
                    </div>
                </div>

                <div class="col-md-1">
                    <div class="card">
                        <button name="search" type="submit" class="btn btn-info label-left b-a-0 waves-effect waves-light">
                            <span class="btn-label"><i class="ti-search"></i></span>&nbsp;&nbsp;&nbsp;Search
                        </button>
                    </div>
                </div>

            </div>
        </form>
        <div class="col-md-3">
            <form action="module/laporan/print.php" method="post" target="_blank">
                <h6>&nbsp;</h6>
                <div class="pull-right">
                    <input placeholder="Dari Tanggal" name="tgl_dari" class="form-control" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" id="date" value="<?php echo $tgl_dari ?>" hidden>
                    <input placeholder="Sampai Tanggal" name="tgl_sampai" class="form-control" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" id="date" value="<?php echo $tgl_sampai ?>" hidden>

                    <!-- <input type="submit" class="btn btn-info label-left b-a-0 waves-effect waves-light" name="cetak" value="Cetak Laporan"> -->
                    <button name="search" type="submit" class="btn btn-info label-left b-a-0 waves-effect waves-light">
                        <span class="btn-label"><i class="ti-book"></i></span>&nbsp;&nbsp;&nbsp;Cetak Laporan
                    </button>
                </div>
            </form>

        </div>
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <!-- <h5 class="card-title">Basic Datatable</h5> -->
                        <div class="table-responsive">
                            <table id="zero_config" class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nama Konsumen</th>
                                        <th>Rating & Komentar</th>
                                        <th>Jumlah Produk</th>
                                        <th>Tanggal Transaksi</th>
                                        <th>Metode Pembayaran</th>
                                        <th>Total Harga</th>
                                        <th>Aksi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <?php
                                    $i = 1;

                                    $arrTotal = array();
                                    while ($p = mysqli_fetch_array($hasil)) {
                                        array_push($arrTotal, $p["total"]);
                                        $total_semua = array_sum($arrTotal);
                                        $nama = mysqli_fetch_array(mysqli_query($konek, "select * from tb_konsumen where id_konsumen='" . $p['id_konsumen'] . "'"));
                                        $jumlah = mysqli_fetch_array(mysqli_query($konek, "select count(*) as jumlah from tb_item_transaksi where id_transaksi='" . $p['id_transaksi'] . "'"));
                                    ?>
                                        <tr>
                                            <td><?= $i++ ?></td>
                                            <td>
                                                <?= $nama["nama"] ?><br>
                                                <p class="font-90 text-muted m-b-1"><?= $nama["no_telp"] ?></p>
                                            </td>
                                            <td><B><?= $p['rating'] ?></B>&nbsp;
                                                <?php if ($p['rating'] == "") {
                                                } else {
                                                    echo '<img src="public/img/star.png" height="15">';
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
                                                // foreach ($this->Transaksi_model->get_data_retur_by_id($p['id_transaksi']) as $r) {
                                                //     echo getRupiah($this->Produk_model->get_data_id($r['id_produk'])['harga']);
                                                // }
                                                ?>
                                            </td>
                                            <td>
                                                <div class="avatar-group">
                                                    <a href="?module=transaksi&aksi=detail&id_konsumen=<?= $p['id_konsumen'] . '&id_transaksi=' . $p['id_transaksi'] ?>" class="btn btn-sm btn-primary">Lihat Pesanan</a>
                                                </div>
                                            </td>
                                        </tr>
                                    <?php } ?>
                                    <thead>
                                    </thead>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>