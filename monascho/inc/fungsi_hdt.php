<?php
function sukses_masuk($in_username, $pass)
{
  include "koneksi_db.php";

  $konek = mysqli_connect($server, $username, $password, $database);

  $login = mysqli_query($konek, "SELECT * FROM tb_administrator WHERE username='$in_username' AND password='$pass'");
  $ketemu = mysqli_num_rows($login);
  $r = mysqli_fetch_array($login);
  if ($ketemu > 0) {
    session_start();
    include "timeout.php";
    $_SESSION['id']     = $r['id'];
    $_SESSION['username']     = $r['username'];
    $_SESSION['passuser']     = $r['password'];
    $_SESSION['nama']    = $r['nama'];


    header('location:admin/?module=dashboard');
    // if ($r['username'] == "admin" || $r['posisi'] == "guru") {
    //   header('location:admin/?module=dashboard');
    // } else if ($r['posisi'] == "kepala") {
    //   header('location:admin/?module=dashboard');
    // } else {
    //   salah_username($in_username);
    // }

    // session timeout
    $_SESSION['login'] = 1;
    timer();
  }
  return false;
}

function msg()
{
  echo "<link href='css/screen.css' rel='stylesheet' type='text/css'>
  <link href='css/reset.css' rel='stylesheet' type='text/css'>
  <link href='css/style_button.css' rel='stylesheet' type='text/css'>
  <center><br><br><br><br><br><br>Maaf, silahkan cek kembali <b>Username</b> dan <b>Password</b> Anda<br><br>Kesalahan $_SESSION[salah]";
  echo "<div> <a href='index.php'><img src='assets/img/kunci.png'  height=176 width=176></a>
  </div>";
  echo "<input type=button class='button buttonblue mediumbtn' value='KEMBALI' onclick=location.href='index.php'></a></center>";
  return false;
}

function salah_blokir($username)
{
  echo "<link href='css/screen.css' rel='stylesheet' type='text/css'>
  <link href='css/reset.css' rel='stylesheet' type='text/css'>
  <link href='css/style_button.css' rel='stylesheet' type='text/css'>
  <center><br><br><br><br><br><br>Maaf, Username <b>$username</b> telah <b>TERBLOKIR</b>, silahkan hubungi Administrator.";
  echo "<div style=''> <a href='index.php'><img src='assets/img/kunci.png'  height=176 width=176></a>
  </div>";
  echo "<input type=button class='button buttonblue mediumbtn' value='KEMBALI' onclick=location.href='index.php'></a></center>";
  return false;
}
function salah_username($username)
{
  echo "<link href='css/screen.css' rel='stylesheet' type='text/css'>
  <link href='css/reset.css' rel='stylesheet' type='text/css'>
  <link href='css/style_button.css' rel='stylesheet' type='text/css'>
  <center><br><br><br><br><br><br>Maaf, Username <b>$username</b> tidak dikenal.";
  echo "<div> <a href='index.php'><img src='assets/img/kunci.png'  height=176 width=176></a>
  </div>";
  echo "<input type=button class='button buttonblue mediumbtn' value='KEMBALI' onclick=location.href='index.php'></a></center>";
  return false;
}

function salah_password()
{
  echo "<link href='css/screen.css' rel='stylesheet' type='text/css'>
  <link href='css/reset.css' rel='stylesheet' type='text/css'>
  <link href='css/style_button.css' rel='stylesheet' type='text/css'>
  <center><br><br><br><br><br><br>Maaf, silahkan cek kembali <b>Password</b> Anda<br><br>Kesalahan $_SESSION[salah]";
  echo "<div> <a href='index.php'><img src='assets/img/kunci.png'  height=176 width=176></a>
  </div>";
  echo "<input type=button class='button buttonblue mediumbtn' value='KEMBALI' onclick=location.href='index.php'></a></center>";
  return false;
}

/*function blokir($username){
	mysql_query($sql);	 
	session_destroy();
	 return false;
}    */
