<?php

$aksi = "module/produk/produk_aksi.php";
$action = $_GET['id'] ?? '';
switch ($_GET['aksi'] ?? '') {
    default:
?>
        <?php
        $hasil = mysqli_query($konek, "SELECT * FROM tb_produk ORDER BY id desc");
        ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Daftar Produk</h4>
                        <div class="ms-auto text-end">
                            <nav aria-label="breadcrumb">
                                <a href="?module=produk&aksi=tambah" class="btn btn-rounded btn-primary"><i class="ti-write"></i>&nbsp;Tambah Produk</a>
                                <!-- <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Library</li>
                        </ol> -->
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
                    <div class="col-md-12">
                        <div class="card">
                            <!-- <div class="card-body">
                        <h4 class="card-title mb-0">Daftar Produk</h4>
                    </div> -->
                            <div class="comment-widgets scrollable">
                                <!-- Comment Row -->
                                <?php
                                $i = 1;
                                while ($key = mysqli_fetch_array($hasil)) {
                                    $harga_diskon = (int)$key['harga'] - (($key['diskon'] / 100) * (int)$key['harga']);
                                ?>
                                    <div class="d-flex flex-row comment-row mt-0">
                                        <div class="p-2"><img src="public/img/produk/<?= $key["foto"] ?>" style="height: 60px; width:60px" alt="user" width="50" class="rounded-circle"></div>
                                        <div class="comment-text w-100">
                                            <h6 class="font-medium"><?= $key["nama"] ?></h6>
                                            <span class="mb-3 d-block"><span class="badge rounded-pill bg-info"><?= getRupiah($harga_diskon) ?></span>
                                                <text color="red"><?= "Diskon " . $key["diskon"] . "%" ?></text>
                                                <span class="mb-3 d-block"><?= "Stok " . $key["stok"] ?></span>
                                                <small class="text-muted">Deskripsi : </small>
                                                <span class="mb-3 d-block"><?= $key["deskripsi"] ?></span>
                                                <div class="comment-footer">
                                                    <!-- <span class="text-muted float-end">April 14, 2021</span> -->
                                                    <a href="?module=produk&aksi=edit&id_produk=<?php echo $key["id"]; ?>" class="btn btn-cyan btn-sm text-white">Ubah</a>
                                                    <!-- <a href="#" data-toggle="modal" data-target="#deleteModal" data-id="<?php echo $key['id']; ?>" class="btn btn-danger btn-sm text-white">
                                            Hapus
                                        </a> -->
                                                    <a onclick="myFunction('<?= $key['id'] ?>');" class="btn btn-danger btn-sm text-white">
                                                        <font color="white">Hapus</font>
                                                    </a>
                                                </div>
                                        </div>
                                    </div>
                                <?php } ?>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- row -->

                <!-- ============================================================== -->
                <!-- End PAge Content -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- Right sidebar -->
                <!-- ============================================================== -->
                <!-- .right-sidebar -->
                <!-- ============================================================== -->
                <!-- End Right sidebar -->
                <!-- ============================================================== -->
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
        </div>

        <script>
            function myFunction(recertid) {
                swal({
                        title: "",
                        text: "Apakah Anda yakin ingin menghapus data ini ? Semua data yang berkaitan dengan produk ini akan terhapus",
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
                            window.location = "<?php echo $aksi ?>?module=produk&aksi=hapus&id=" + recertid;
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
                        <h4 class="page-title">Tambah Produk</h4>

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
                                <form action="<?php echo $aksi ?>?module=produk&aksi=tambah" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Nama Produk </label>
                                        <input type="text" class="form-control" name="nama" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Harga </label>
                                        <input type="text" id="harga" class="form-control" name="harga" id="date-mask" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Stok </label>
                                        <input type="text" class="form-control" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" min="1" max="100000" name="stok" id="date-mask" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Diskon </label>
                                        <input type="text" class="form-control" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" min="1" max="100" name="diskon" id="date-mask" placeholder="" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Produk 1</label><br>
                                        <input type="file" name="foto1" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Produk 2</label><br>
                                        <input type="file" name="foto2" class="custom-file-input" id="validatedCustomFile">
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Produk 3</label><br>
                                        <input type="file" name="foto3" class="custom-file-input" id="validatedCustomFile">
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Produk 4</label><br>
                                        <input type="file" name="foto4" class="custom-file-input" id="validatedCustomFile">
                                    </div>
                                    <div class="form-group">
                                        <label>Foto Produk 5</label><br>
                                        <input type="file" name="foto5" class="custom-file-input" id="validatedCustomFile">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Deskripsi </label>
                                        <textarea class="form-control" name="deskripsi"></textarea>
                                        <!-- <input type="text" class="form-control" name="stok" id="date-mask" placeholder=""> -->
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #green; border-color:#E56299; color:#white;">Simpan</button>
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

        <script type="text/javascript">
            var rupiah = document.getElementById("harga");
            rupiah.addEventListener("keyup", function(e) {
                rupiah.value = formatRupiah(this.value, "Rp. ");
            });

            function formatRupiah(angka, prefix) {
                var number_string = angka.replace(/[^,\d]/g, "").toString(),
                    split = number_string.split(","),
                    sisa = split[0].length % 3,
                    rupiah = split[0].substr(0, sisa),
                    ribuan = split[0].substr(sisa).match(/\d{3}/gi);

                if (ribuan) {
                    separator = sisa ? "." : "";
                    rupiah += separator + ribuan.join(".");
                }

                rupiah = split[1] != undefined ? rupiah + "," + split[1] : rupiah;
                return prefix == undefined ? rupiah : rupiah ? "Rp. " + rupiah : "";
            }
        </script>


    <?php
        break;
    case "edit":
        $produk = mysqli_fetch_array(mysqli_query($konek, "select * from tb_produk where id='" . $_GET['id_produk'] . "'"));
    ?>

        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-12 d-flex no-block align-items-center">
                        <h4 class="page-title">Edit Produk</h4>

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
                                <form action="<?php echo $aksi ?>?module=produk&aksi=edit&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Nama Produk </label>
                                        <input type="text" class="form-control" name="nama" value="<?= $produk["nama"] ?>" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Harga </label>
                                        <input type="text" id="harga" class="form-control" name="harga" value="<?= $produk["harga"] ?>" id="date-mask" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Stok </label>
                                        <input type="text" class="form-control" value="<?= $produk["stok"] ?>" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" min="1" max="100000" name="stok" id="date-mask" placeholder="">
                                    </div>
                                    <div class="form-group mt-3">
                                        <label>Diskon </label>
                                        <input type="text" class="form-control" value="<?= $produk["diskon"] ?>" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" min="1" max="100" name="diskon" id="date-mask" placeholder="" required>
                                    </div>
                                    <!-- <div class="form-group">
                                <label>Foto Produk </label><br>
                                <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                            </div> -->
                                    <div class="form-group mt-3">
                                        <label>Deskripsi </label>
                                        <textarea class="form-control" name="deskripsi"><?= $produk["deskripsi"] ?></textarea>
                                        <!-- <input type="text" class="form-control" name="stok" id="date-mask" placeholder=""> -->
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <form action="<?php echo $aksi ?>?module=produk&aksi=editgambar&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Ubah Foto Produk 1</label>
                                        <br>
                                        <img style="width: 20%;" src="<?php echo (@is_array(getimagesize('public/img/produk/' . $produk["foto"]))) ? 'public/img/produk/' . $produk["foto"] : 'public/img/produk/' . "no-img.png" ?>" />
                                        <input type="hidden" class="form-control" name="fotonama" value="<?php echo $produk['foto'] ?>">
                                        <input type="hidden" class="form-control" name="fotoke" value="foto">
                                    </div>
                                    <div class="form-group">
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <form action="<?php echo $aksi ?>?module=produk&aksi=editgambar&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Ubah Foto Produk 2</label>
                                        <br>
                                        <img style="width: 20%;" src="<?php echo (@is_array(getimagesize('public/img/produk/' . $produk["foto2"]))) ? 'public/img/produk/' . $produk["foto2"] : 'public/img/produk/' . "no-img.png" ?>" />
                                        <input type="hidden" class="form-control" name="fotonama" value="<?php echo $produk['foto2'] ?>">
                                        <input type="hidden" class="form-control" name="fotoke" value="foto2">
                                    </div>
                                    <div class="form-group">
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <form action="<?php echo $aksi ?>?module=produk&aksi=editgambar&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Ubah Foto Produk 3</label>
                                        <br>
                                        <img style="width: 20%;" src="<?php echo (@is_array(getimagesize('public/img/produk/' . $produk["foto3"]))) ? 'public/img/produk/' . $produk["foto3"] : 'public/img/produk/' . "no-img.png" ?>" />
                                        <input type="hidden" class="form-control" name="fotonama" value="<?php echo $produk['foto3'] ?>">
                                        <input type="hidden" class="form-control" name="fotoke" value="foto3">
                                    </div>
                                    <div class="form-group">
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <form action="<?php echo $aksi ?>?module=produk&aksi=editgambar&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Ubah Foto Produk 4</label>
                                        <br>
                                        <img style="width: 20%;" src="<?php echo (@is_array(getimagesize('public/img/produk/' . $produk["foto4"]))) ? 'public/img/produk/' . $produk["foto4"] : 'public/img/produk/' . "no-img.png" ?>" />
                                        <input type="hidden" class="form-control" name="fotonama" value="<?php echo $produk['foto4'] ?>">
                                        <input type="hidden" class="form-control" name="fotoke" value="foto4">
                                    </div>
                                    <div class="form-group">
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <form action="<?php echo $aksi ?>?module=produk&aksi=editgambar&id_produk=<?php echo $produk["id"] ?>" method="post" enctype="multipart/form-data" class="form-horizontal form-label-left">
                                    <!-- <h5 class="card-title mb-0">Tambah Produk</h5> -->
                                    <div class="form-group mt-3">
                                        <label>Ubah Foto Produk 5</label>
                                        <br>
                                        <img style="width: 20%;" src="<?php echo (@is_array(getimagesize('public/img/produk/' . $produk["foto5"]))) ? 'public/img/produk/' . $produk["foto5"] : 'public/img/produk/' . "no-img.png" ?>" />
                                        <input type="hidden" class="form-control" name="fotonama" value="<?php echo $produk['foto5'] ?>">
                                        <input type="hidden" class="form-control" name="fotoke" value="foto5">
                                    </div>
                                    <div class="form-group">
                                        <input type="file" name="foto" class="custom-file-input" id="validatedCustomFile" required>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" style="background-color: #0099ff; border-color:#E56299; color:white;">Simpan</button>
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

        <script type="text/javascript">
            var rupiah = document.getElementById("harga");
            rupiah.addEventListener("keyup", function(e) {
                rupiah.value = formatRupiah(this.value, "Rp. ");
            });

            function formatRupiah(angka, prefix) {
                var number_string = angka.replace(/[^,\d]/g, "").toString(),
                    split = number_string.split(","),
                    sisa = split[0].length % 3,
                    rupiah = split[0].substr(0, sisa),
                    ribuan = split[0].substr(sisa).match(/\d{3}/gi);

                if (ribuan) {
                    separator = sisa ? "." : "";
                    rupiah += separator + ribuan.join(".");
                }

                rupiah = split[1] != undefined ? rupiah + "," + split[1] : rupiah;
                return prefix == undefined ? rupiah : rupiah ? "Rp. " + rupiah : "";
            }
        </script>

<?php
        break;
}
?>