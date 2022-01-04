<?php

$aksi = "module/informasi/informasi_aksi.php";
$action = $_GET['id'] ?? '';
switch ($_GET['aksi'] ?? '') {
    default:
?>

        <?php
        $hasil = mysqli_query($konek, "SELECT * FROM tb_informasi ORDER BY urutan desc");
        ?>
        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Informasi</h4>
                        <div class="ms-auto text-end">
                            <nav aria-label="breadcrumb">
                                <a href="?module=informasi&aksi=tambah" class="btn btn-rounded btn-primary"><i class="ti-write"></i>&nbsp;Tambah Informasi</a>
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
                <!-- Sales Cards  -->
                <!-- ============================================================== -->


                <div class="row">
                    <!-- column -->
                    <div class="col-lg-12">
                        <!-- card new -->
                        <div class="card">

                            <?php
                            $i = 1;
                            while ($key = mysqli_fetch_array($hasil)) {
                            ?>
                                <ul class="list-style-none">
                                    <br>
                                    <div class="comment-footer" style="margin-left: 20px;">
                                        <a onclick="myFunction('<?= $key['id'] ?>');" class="btn btn-danger btn-sm text-white">
                                            <font color="white">Hapus</font>
                                        </a>
                                    </div>
                                    <li class="d-flex no-block card-body">
                                        <!-- <i class="fa fa-check-circle w-30px mt-1"></i> -->
                                        <img src="public/img/informasi/<?= $key["foto"] ?>" style="height: 50px; width:50px" alt="user" width="50" class="rounded-circle">
                                        <div style="padding-left: 20px;">
                                            <div>
                                                <a href="#" class="mb-0 font-medium p-0"><?= $key["judul"] ?></a>
                                                <span class="text-muted"><?= $key["ket"] ?></span>
                                            </div>
                                            <br>
                                            <br>
                                            <div class="ms-auto">
                                                <div class="tetx-right">
                                                    <h5 class="text-muted mb-0"><?= $key["tgl"] ?></h5>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                                <hr>
                            <?php } ?>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function myFunction(recertid) {
                swal({
                        title: "",
                        text: "Apakah Anda yakin ingin menghapus data ini?",
                        type: "warning",
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Hapus!",
                        cancelButtonText: "Batal!",
                        closeOnConfirm: false,
                        closeOnCancel: false,
                    },
                    function(isConfirm) {
                        if (isConfirm) {
                            window.location = "<?php echo $aksi ?>?module=informasi&aksi=hapus&id=" + recertid;
                        } else {
                            //return false;
                            swal({
                                title: "",
                                text: "Dibatalkan!",
                                type: "error",
                                timer: 1000
                            });
                        }
                    });
            }
        </script>

    <?php
        break;
    case "tambah":
    ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Tambah Informasi</h4>

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
                                <form action="<?php echo $aksi ?>?module=informasi&aksi=tambah" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Judul </label>
                                        <input type="text" class="form-control" name="judul" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Informasi </label><br>
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Deskripsi </label>
                                        <textarea class="form-control" name="ket"></textarea>
                                        <!-- <input type="text" class="form-control" name="stok" id="date-mask" placeholder=""> -->
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color#0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>

<?php
        break;
}
?>