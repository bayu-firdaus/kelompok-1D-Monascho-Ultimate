<?php
error_reporting(0);
include "inc/inc.koneksi.php";
include "inc/fungsi_hdt.php";

function anti_injection($data)
{
	$filter = mysql_real_escape_string(stripslashes(strip_tags(htmlspecialchars($data, ENT_QUOTES))));
	return $filter;
}
$username = ($_POST['username']);
$pass	 = ($_POST['password']);
if (!ctype_alnum($pass)) {
	
?>
	<script>
		alert('Kosong');
		window.location.href = 'index.php';
	</script>
<?php
} else {
	$login	= mysqli_query($konek, "SELECT * FROM tb_administrator WHERE username='$username'");
	$ketemu	= mysqli_num_rows($login);
	if ($ketemu > 0) {
		$r		= mysqli_fetch_array($login);
		$pwd	= $r['password'];
		if ($pwd == $pass) {
			$_SESSION['login'] = 1;
			sukses_masuk($username, $pass);
		} else {
			header('location:index.php');
		}
	} else {
		$_SESSION['login'] = -1;
		header('location:index.php');
	}
}
