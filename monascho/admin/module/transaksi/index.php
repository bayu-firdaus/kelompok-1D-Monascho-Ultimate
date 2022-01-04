<?php

$aksi = "module/transaksi/transaksi_aksi.php";
$action = $_GET['id'] ?? '';
switch ($_GET['aksi'] ?? '') {
    default:
?>
        <?php
        $hasil = mysqli_query($konek, "SELECT * FROM tb_transaksi WHERE status < 7 ORDER BY urutan desc");
        ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Pesanan Masuk</h4>
                        <!-- <div class="ms-auto text-end">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Library</li>
                        </ol>
                    </nav>
                </div> -->
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
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
                                                <th>Nama</th>
                                                <!-- <th>Chat</th> -->
                                                <th>Jumlah Produk</th>
                                                <th>Tanggal Transaksi</th>
                                                <th>Metode Pembayaran</th>
                                                <th>Total Harga</th>
                                                <th>Status</th>
                                                <th>Aksi</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <?php
                                            $i = 1;
                                            $arr = array();
                                            while ($p = mysqli_fetch_array($hasil)) {
                                                $nama = mysqli_fetch_array(mysqli_query($konek, "select * from tb_konsumen where id_konsumen='" . $p['id_konsumen'] . "'"));
                                                $jumlah = mysqli_fetch_array(mysqli_query($konek, "select count(*) as jumlah from tb_item_transaksi where id_transaksi='" . $p['id_transaksi'] . "'"));
                                            ?>
                                                <tr>
                                                    <td><?= $i++ ?></td>
                                                    <td><?= $nama["nama"] ?><br></td>
                                                    <td><?= $jumlah["jumlah"] . " Produk"; ?></td>
                                                    <td><?= $p['tgl_transaksi'] ?></td>
                                                    <td><?= getPembayaran($p['pembayaran']) ?></td>
                                                    <td><?= getRupiah($p['total']) ?></td>
                                                    <td><?= getStatus($p['status']) ?></td>
                                                    <td>
                                                        <a href="?module=transaksi&aksi=detail&id_konsumen=<?= $p['id_konsumen'] . '&id_transaksi=' . $p['id_transaksi'] ?>" class="btn btn-sm btn-primary">Lihat Pesanan</a>

                                                        <?php if ($p["status"] == "0") { ?>
                                                            <?php if ($p['pembayaran'] == 1) { ?>
                                                                <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $p['id_transaksi'] ?>&status=1" class="btn btn-sm btn-success">Terima Pesanan</a>
                                                            <?php } else { ?>
                                                                <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $p['id_transaksi'] ?>&status=4" class="btn btn-sm btn-success">Terima Pesanan</a>
                                                            <?php } ?>
                                                        <?php } else if ($p["status"] == "2") { ?>
                                                            <a href="?module=transaksi&aksi=detail_pembayaran&id_transaksi=<?= $p['id_transaksi'] ?>" class="btn btn-sm btn-warning">Bukti Pembayaran</a>
                                                            <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $p['id_transaksi'] ?>&status=3" class="btn btn-sm btn-success">Konfirmasi Pembayran</a>
                                                        <?php } else if ($p["status"] == "3") { ?>
                                                            <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $p['id_transaksi'] ?>&status=4" class="btn btn-sm btn-success">Packing Pesanan</a>
                                                        <?php } else if ($p["status"] == "4") { ?>
                                                            <a onclick="myFunction('<?= $p['id_transaksi'] ?>&status=5');" class="btn btn-sm btn-success">
                                                                <font color="white">Kirim Pesanan</font>
                                                            </a>
                                                            <!-- <a href="" data-toggle="modal" data-target="#deleteModal" data-id="<?php echo $p['id_transaksi']; ?>/5" class="btn btn-sm btn-success">
                                                        <font color="white">Kirim Pesanan</font>
                                                    </a> -->
                                                        <?php } else if ($p["status"] == "6") { ?>
                                                            <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $p['id_transaksi'] ?>&status=5" class="btn btn-sm btn-success">Selesai</a>
                                                        <?php } ?>
                                                    </td>
                                                </tr>
                                            <?php } ?>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br><br><br><br><br><br><br><br><br><br>
        </div>
        </div>

        <script>
            function myFunction(recertid, jenis) {

                swal({
                    title: "",
                    text: "Nama Kurir : <br><input type='text' class='custom-file-input' id='nama' required><br><br>Nomor Telephone : <br><input type='text' class='custom-file-input' id='nomor' required>",
                    // --------------^-- define html element with id
                    html: true,
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                    animation: "slide-from-top",
                    inputPlaceholder: "Write something"
                }, function(inputValue) {
                    if (inputValue === false) return false;
                    if (inputValue === "") {
                        swal.showInputError("You need to write something!");
                        return false
                    }
                    // get value using textarea id
                    var nama = document.getElementById('nama').value
                    var nomor = document.getElementById('nomor').value
                    window.location = "<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=" + recertid + "&nama=" + nama + "&nomor=" + nomor
                });
            }
        </script>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script>
            $(function() {
                $('#deleteModal').on('show.bs.modal', function(event) {
                    var t = $(event.relatedTarget) // Button that triggered the modal
                    var id = t.data('id') // Extract info from data-* attributes
                    var modal = $(this);
                    modal.find('.modal-body input').val(id);
                })
            });
        </script>

        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form action="satuan/remove" method="POST">

                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Perhatatian!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Apa anda yakin ingin menghapus data ini ? </p>
                            <input type="text" name="id_satuan" hidden>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Batal</button>
                            <button type="submit" class="btn btn-primary">Proses</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    <?php
        break;
    case "detail":
        $hasil = mysqli_query($konek, "SELECT * FROM tb_item_transaksi WHERE id_transaksi ='" . $_GET['id_transaksi'] . "' ORDER BY id_item desc");
        $transaksis = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='" . $_GET['id_transaksi'] . "'"));
    ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Detail Pesanan</h4>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="card card-body printableArea">

                            <div class="row">

                                <div class="col-md-12">
                                    <div class="table-responsive mt-5" style="clear: both;">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th class="text-center">#</th>
                                                    <th>Nama Produk</th>
                                                    <th class="text-end">Harga Satuan</th>
                                                    <th class="text-end">Kuantitas</th>
                                                    <th class="text-end">Harga Total</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <?php
                                                $i = 1;
                                                $arr = array();
                                                while ($b = mysqli_fetch_array($hasil)) {
                                                    $produks = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $b['id_produk'] . "'"));
                                                    $harga_diskon = (int) $produks["harga"] - (($produks["diskon"] / 100) * (int) $produks["harga"]);
                                                    $harga = $harga_diskon * $b["kuantitas"];
                                                    $total = array_push($arr, $harga);
                                                    $total_harga = array_sum($arr);
                                                ?>
                                                    <tr>
                                                        <td class="text-center"><?= $i++ ?></td>
                                                        <td><?= $produks['nama'] ?></td>
                                                        <td class="text-end"><?= getRupiah($harga_diskon) ?></td>
                                                        <td class="text-end"> <?= $b["kuantitas"]  ?> </td>
                                                        <td class="text-end"> <?= getRupiah($harga) ?> </td>
                                                    </tr>
                                                <?php } ?>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="pull-right mt-4 text-end">
                                        <!-- <p>Sub - Total amount: $13,848</p>
                                <p>vat (10%) : $138 </p>
                                <hr> -->
                                        <p>Sub total : <?= getRupiah($total_harga) ?> </p>
                                        <p>Ongkir : <?= getRupiah($transaksis['ongkir']) ?> </p>
                                        <h3><b>Total :</b> <?= getRupiah($total_harga + $transaksis['ongkir']) ?></h3>
                                    </div>
                                    <div class="clearfix"></div>
                                    <hr>
                                    <!-- <div class="text-end">
                                <button class="btn btn-danger text-white" type="submit"> Proceed to payment </button>
                            </div> -->
                                </div>
                            </div>

                            <div class="card-body">
                                <h4 class="card-title">Keterangan Lokasi Konsumen</h4>
                                <p class="card-description"> <?php echo $transaksis['alamat'] ?> </p>
                                <iframe width="100%" height="500" id="gmap_canvas" src="https://www.google.com/maps?q=<?php echo $transaksis['lat'] ?>,<?php echo $transaksis['lng'] ?>&t=&z=13&ie=UTF8&iwloc=&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
        </div>

    <?php
        break;
    case "detail_pembayaran":
        $data = mysqli_fetch_array(mysqli_query($konek, "select * from tb_transaksi where id_transaksi='" . $_GET['id_transaksi'] . "'"));
    ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Full Width</h4>
                        <div class="ms-auto text-end">
                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Library</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Bukti Pembayaran</h5>
                                <img style="width: 100%; width: 100%;" class="img-fluid" src="../webservice/fbukti/<?= $data["foto"] ?>" alt="">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <!-- <div class="card">
                    <div class="card-body">
                        <br>
                        <h6 class="card-title">Atas Nama</h6>
                        <p><?= $data["nama_pengirim"] ?></p>
                        <br>
                        <h6 class="card-title">Atas Nama</h6>
                        <p><?= $data["nomor_pengirim"] ?></p>
                    </div>
                </div> -->
                        <a href="<?php echo $aksi ?>?module=transaksi&aksi=terima&id_transaksi=<?= $data['id_transaksi'] ?>&status=3" class="btn btn-primary btn-block btn-lg">Konfirmasi Pembayaran</a>
                    </div>
                </div>
            </div>
        </div>
        </div>

<?php
        break;
}
?>