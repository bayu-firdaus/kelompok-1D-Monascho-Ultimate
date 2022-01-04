<?php
	include "../../koneksi_db.php";
	$uriSegments = explode("/", parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH));
	
	$konek = mysqli_connect($server, $username, $password, $database);

	if (mysqli_connect_errno()) {
		echo "gagal konek ke database" . mysqli_connect_errno();
	}

	include "../../koneksi_db.php";
    try{
        $koneksi = new PDO('mysql:host='.$server.';dbname='.$database.';', $username, $password);
    }catch (PDOException $e) {
        return 'Koneksi Gagal : ' . $e->getMessage();
    }

	function getRupiah($harga = 0)
	{
		if ($harga != null) {
			return "Rp " . number_format($harga, 0, ",", ".");
		} else {
			return "Rp 0";
		}
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

	function tgl_indo($tgl, $replace_with = '-')
	{
		if (date_is_empty($tgl)) {
			return $replace_with;
		}
		$tanggal = substr($tgl, 8, 2);
		$bulan = getBulan(substr($tgl, 5, 2));
		$tahun = substr($tgl, 0, 4);
		// return $tanggal . ' ' . $bulan . ' ' . $tahun;
		return $tanggal . ' ' . $bulan;
	}

	function date_is_empty($tgl)
	{
		return (is_null($tgl) || substr($tgl, 0, 10) == '0000-00-00');
	}

	function getBulan($bln)
	{
		switch ($bln) {
			case 1:
				return "Jan";
				break;
			case 2:
				return "Feb";
				break;
			case 3:
				return "Mar";
				break;
			case 4:
				return "Apr";
				break;
			case 5:
				return "Mei";
				break;
			case 6:
				return "Jun";
				break;
			case 7:
				return "Jul";
				break;
			case 8:
				return "Ags";
				break;
			case 9:
				return "Sep";
				break;
			case 10:
				return "Okt";
				break;
			case 11:
				return "Nov";
				break;
			case 12:
				return "Des";
				break;
		}
	}
?>
