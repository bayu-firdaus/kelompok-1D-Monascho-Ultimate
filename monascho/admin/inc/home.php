<?php
include ("../koneksi.php");  ?>
<br/>
<div style="margin-right:10%;margin-left:15%" class="alert alert-info alert-dismissable">
<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
<h4><i class="icon fa fa-info"></i>
Welcome <?php echo $_SESSION['nama']; ?>! &nbsp;&nbsp;
Anda berada di halaman "<?php echo $_SESSION['level']; ?>"
</h4>
</div>

<div class="box box-solid box-primary">
<div class="box-header">
<i class="fa fa-info"></i>Informasi
</div>
<div class="box-body">
<h4>Hak Akses sebagai Administrator:</h4>
<li>Mengelola data Pegawai</li>
<li>Mengelola data Absensi Pegawai</li>
<li>Mengelola data Usulan Berkas Pegawai</li>
<li>Mengelola data Pengguna Sistem</li>
</div>
</div><!-- /.row --
