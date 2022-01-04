-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Des 2021 pada 04.23
-- Versi server: 10.4.19-MariaDB
-- Versi PHP: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `monascho2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_administrator`
--

CREATE TABLE `tb_administrator` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_administrator`
--

INSERT INTO `tb_administrator` (`id`, `username`, `password`, `nama`) VALUES
(1, 'admin', 'admin', 'Administrator');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_informasi`
--

CREATE TABLE `tb_informasi` (
  `urutan` int(11) NOT NULL,
  `id` text NOT NULL,
  `judul` varchar(100) NOT NULL,
  `ket` text NOT NULL,
  `tgl` varchar(100) NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_informasi`
--

INSERT INTO `tb_informasi` (`urutan`, `id`, `judul`, `ket`, `tgl`, `foto`) VALUES
(12, '136b622ed7e644babc7397502f4d1056', 'Monascho Curcumin 1', 'Monascho Curcumin dapat meningkatkan daya tahan tubuh bayi pada umur 1 hingga 4 tahun, herbal ini dapat mencegah bayi terserang penyakit-penyakit yang berbahaya', '2021-12-20 03:56:05', 'e8b54f4dba3844b5b7901b4d25110a0a.jpg'),
(13, 'f866903bd7154e4ea2e361ae8de819bb', 'Monascho Aloe', 'Monascho Curcumin dapat meningkatkan daya tahan tubuh remaja pada umur 14 hingga 19 tahun, herbal ini dapat mencegah remaja terserang penyakit-penyakit yang berbahaya', '2021-12-20 03:56:54', 'daf0eac76815460bb303eeb6e613ef63.jpg'),
(14, '7b8821a3097d445dabc2b81d08724970', 'Monascho Fit', 'Monascho Curcumin dapat meningkatkan daya tahan tubuh lansia pada umur 45 hingga 70 tahun, herbal ini dapat mencegah lansia terserang penyakit-penyakit yang berbahaya', '2021-12-20 03:57:44', 'ba3ab9033a924653bc1cf2d6c487ea09.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_item_transaksi`
--

CREATE TABLE `tb_item_transaksi` (
  `id_item` int(11) NOT NULL,
  `id_transaksi` text NOT NULL,
  `id_konsumen` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `kuantitas` int(20) NOT NULL,
  `id_retur` text NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_item_transaksi`
--

INSERT INTO `tb_item_transaksi` (`id_item`, `id_transaksi`, `id_konsumen`, `id_produk`, `kuantitas`, `id_retur`, `status`) VALUES
(96, '31b5dd7d55314877bff77291ba4f0b1b', 29, 18, 2, '', 0),
(99, '5389be5669d043a9989c0340240e79b3', 30, 21, 2, '', 0),
(102, 'd2847cb68d5a49f7aba2cf992c5807e4', 30, 21, 1, '', 0),
(103, '966fe063c6a4401c945ca45c534b2b10', 29, 21, 1, '', 0),
(105, '458c78efd04d4a2aa7f517105d8a1118', 37, 21, 2, '', 0),
(107, '7899e322a3534c4488dbca23c9473bdf', 37, 23, 1, '', 0),
(109, '7899e322a3534c4488dbca23c9473bdf', 37, 21, 3, '', 0),
(110, '6b0b22287f35438ab34228bc592bf35e', 38, 23, 2, '', 0),
(111, '6b0b22287f35438ab34228bc592bf35e', 38, 21, 1, '', 0),
(112, 'ad5789f0af344e0eb01f4b5b27d81a17', 38, 23, 1, '', 0),
(113, '06fc99e26f23411eadd42e92d0c7d7e9', 38, 23, 3, '', 0),
(114, 'e7476c984a684b6b8e5c6f9a1efe6bc3', 1, 23, 1, '', 0),
(115, 'c3703a08c708462fa109f13315d7b9c9', 1, 21, 1, '', 0),
(118, '9aa7469819414a6da6382f352065dfdc', 38, 23, 2, '', 0),
(119, '9aa7469819414a6da6382f352065dfdc', 38, 21, 2, '435fff3a509a414caf8b99291033a11a', 3),
(121, '6d5f008b1089409cbe0e6fe517fcc0b4', 40, 21, 1, '', 0),
(122, '6d5f008b1089409cbe0e6fe517fcc0b4', 40, 24, 2, '', 0),
(123, '32641610abea424fbe7870e0c1c65339', 41, 23, 3, '', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_keranjang`
--

CREATE TABLE `tb_keranjang` (
  `id_keranjang` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_konsumen` int(11) NOT NULL,
  `kuantitas_item` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_keranjang`
--

INSERT INTO `tb_keranjang` (`id_keranjang`, `id_produk`, `id_konsumen`, `kuantitas_item`) VALUES
(91, 21, 41, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_konsumen`
--

CREATE TABLE `tb_konsumen` (
  `id_konsumen` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `kecamatan` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kodepos` varchar(100) NOT NULL,
  `tgl_lahir` varchar(50) NOT NULL,
  `jk` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `tgl_create` varchar(20) NOT NULL,
  `token` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_konsumen`
--

INSERT INTO `tb_konsumen` (`id_konsumen`, `nama`, `no_telp`, `kecamatan`, `alamat`, `kodepos`, `tgl_lahir`, `jk`, `email`, `password`, `tgl_create`, `token`) VALUES
(40, 'Alva Paris', '082259500321', '', '-', '', '1997-10-25', 'Laki-Laki', 'alva@gmail.com', '12345', '2021-10-25 13:35:28', 'cftpcAheTUW0sSuj0O1ZQz:APA91bFolaJ-qjoT_B2NvWNVCFiRqKq8YlCfuHXVgustU2kxye-65-0GApxSKephJc6vuZjxCSU2S7HHjeLx3O5GVydxJ2osmm_akpeKuo9n0JzeO30mDBX5sOgBEIjzR2EXcduKOVf-'),
(41, 'Diki', '082259500319', '', 'alamat', '', '2021-11-29', 'Laki-Laki', 'diki@gmail.com', '12345', '2021-11-29 04:01:08', 'cP3ojDeSTI2FAow1IaovAn:APA91bHOAS26DnrOyU7Ue_WFRMZcpZCYW86rbaSIzkWna9dJUaV6kd90NWyM9P9eTs4jIQUFEH6aPC_AeD41s7wq0UnOiKFsgM33C63Iqqz4iHvpGpgjD2WIb9pcqSFTJAUElb0GY-dn');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_lacakpesanan`
--

CREATE TABLE `tb_lacakpesanan` (
  `id` int(11) NOT NULL,
  `id_transaksi` text NOT NULL,
  `tgl` varchar(60) NOT NULL,
  `jam` varchar(60) NOT NULL,
  `pesan` text NOT NULL,
  `nomor` text NOT NULL,
  `jenis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_lacakpesanan`
--

INSERT INTO `tb_lacakpesanan` (`id`, `id_transaksi`, `tgl`, `jam`, `pesan`, `nomor`, `jenis`) VALUES
(8, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:00:03', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(9, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:06:27', 'Menunggu Konfirmasi Pembayaran', '', 2),
(10, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:07:00', 'Pembayaran Dikonfirmasi', '', 3),
(11, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:07:10', 'Dalam Proses Pengemasan', '', 4),
(12, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:07:28', 'Pesanan anda sudah diantar Oleh Diki', '082259500331', 5),
(13, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:07:38', 'Pesanan Sudah Diterima', '', 6),
(14, '458c78efd04d4a2aa7f517105d8a1118', '2021-07-10', '23:07:45', 'Selesai', '', 7),
(15, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:22:11', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(16, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:24:20', 'Menunggu Konfirmasi Pembayaran', '', 2),
(17, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:24:59', 'Pembayaran Dikonfirmasi', '', 3),
(18, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:25:36', 'Dalam Proses Pengemasan', '', 4),
(19, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:25:54', 'Pesanan anda sudah diantar Oleh Mega', '082259500319', 5),
(20, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:26:03', 'Pesanan Sudah Diterima', '', 6),
(21, '6b0b22287f35438ab34228bc592bf35e', '2021-07-10', '23:26:41', 'Selesai', '', 7),
(22, 'ad5789f0af344e0eb01f4b5b27d81a17', '2021-07-10', '23:29:03', 'Pesanan anda sudah diantar Oleh bh', '8786', 5),
(23, 'ad5789f0af344e0eb01f4b5b27d81a17', '2021-07-10', '23:29:17', 'Pesanan Sudah Diterima', '', 6),
(24, 'ad5789f0af344e0eb01f4b5b27d81a17', '2021-07-10', '23:29:26', 'Selesai', '', 7),
(25, '06fc99e26f23411eadd42e92d0c7d7e9', '2021-07-10', '23:33:50', 'Pesanan Anda Sudah diterima', '', 4),
(26, '06fc99e26f23411eadd42e92d0c7d7e9', '2021-07-10', '23:34:04', 'Pesanan anda sudah diantar Oleh bhb', '08226765656', 5),
(27, '06fc99e26f23411eadd42e92d0c7d7e9', '2021-07-10', '23:34:10', 'Pesanan Sudah Diterima', '', 6),
(28, '06fc99e26f23411eadd42e92d0c7d7e9', '2021-07-10', '23:34:20', 'Selesai', '', 7),
(29, 'e7476c984a684b6b8e5c6f9a1efe6bc3', '2021-09-15', '03:00:58', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(30, 'c3703a08c708462fa109f13315d7b9c9', '2021-09-15', '03:11:21', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(31, 'c3703a08c708462fa109f13315d7b9c9', '2021-09-15', '03:12:08', 'Menunggu Konfirmasi Pembayaran', '', 2),
(32, 'e7476c984a684b6b8e5c6f9a1efe6bc3', '2021-09-15', '03:28:00', 'Menunggu Konfirmasi Pembayaran', '', 2),
(33, '6b0b22287f35438ab34228bc592bf35e', '2021-10-05', '14:23:03', 'Pesanan Sudah Diterima', '', 6),
(34, '6b0b22287f35438ab34228bc592bf35e', '2021-10-05', '14:26:24', 'Selesai', '', 7),
(35, '5c2b0e8df8d640e5915de2edf5e3b9f5', '2021-10-12', '11:39:46', 'Pesanan Anda Sudah diterima', '', 4),
(36, '5c2b0e8df8d640e5915de2edf5e3b9f5', '2021-10-12', '11:40:10', 'Pesanan anda sudah diantar Oleh Dika', '082259500319', 5),
(37, '5c2b0e8df8d640e5915de2edf5e3b9f5', '2021-10-12', '11:44:53', 'Pesanan Anda Sudah diterima', '', 4),
(38, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:51:24', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(39, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:52:34', 'Menunggu Konfirmasi Pembayaran', '', 2),
(40, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:52:44', 'Pembayaran Dikonfirmasi', '', 3),
(41, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:53:32', 'Dalam Proses Pengemasan', '', 4),
(42, '5c2b0e8df8d640e5915de2edf5e3b9f5', '2021-10-12', '11:53:34', 'Dalam Proses Pengemasan', '', 4),
(43, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:54:40', 'Pesanan anda sudah diantar Oleh Dika', '08224003911', 5),
(44, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '11:56:19', 'Pesanan Sudah Diterima', '', 6),
(45, '3920c2af2bff4f77a1d9cfe602133cbf', '2021-10-12', '14:02:53', 'Pesanan anda sudah diantar Oleh Dika', '092823213123123', 5),
(46, '5c2b0e8df8d640e5915de2edf5e3b9f5', '2021-10-12', '14:04:10', 'Pesanan anda sudah diantar Oleh Anda', '082259500311', 5),
(47, '9aa7469819414a6da6382f352065dfdc', '2021-10-12', '14:27:04', 'Pesanan Anda Sudah diterima', '', 4),
(48, '9aa7469819414a6da6382f352065dfdc', '2021-10-12', '14:27:19', 'Pesanan anda sudah diantar Oleh an', '082259500319', 5),
(49, '9aa7469819414a6da6382f352065dfdc', '2021-10-12', '14:55:23', 'Pesanan Sudah Diterima', '', 6),
(50, '9aa7469819414a6da6382f352065dfdc', '2021-10-12', '14:56:33', 'Selesai', '', 7),
(51, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:26:53', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(52, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:38:05', 'Menunggu Konfirmasi Pembayaran', '', 2),
(53, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:40:06', 'Pembayaran Dikonfirmasi', '', 3),
(54, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:45:39', 'Menunggu Konfirmasi Pembayaran', '', 2),
(55, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:48:25', 'Pembayaran Dikonfirmasi', '', 3),
(56, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:48:38', 'Dalam Proses Pengemasan', '', 4),
(57, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:49:24', 'Pesanan anda sudah diantar Oleh Alva', '082259500319', 5),
(58, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:49:46', 'Pesanan Sudah Diterima', '', 6),
(59, '2b3ae9d7ddf14e419de147dab95f318a', '2021-10-25', '14:50:00', 'Selesai', '', 7),
(60, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:40:19', 'Pesanan Anda Sudah diterima, Selesaikan Pembayaran', '', 1),
(61, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:41:06', 'Menunggu Konfirmasi Pembayaran', '', 2),
(62, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:41:30', 'Pembayaran Dikonfirmasi', '', 3),
(63, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:41:54', 'Dalam Proses Pengemasan', '', 4),
(64, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:42:19', 'Pesanan anda sudah diantar Oleh Dika Paris', '082259500319', 5),
(65, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:42:52', 'Pesanan Sudah Diterima', '', 6),
(66, '6d5f008b1089409cbe0e6fe517fcc0b4', '2021-10-25', '19:43:24', 'Selesai', '', 7),
(67, '32641610abea424fbe7870e0c1c65339', '2021-11-29', '11:08:08', 'Pesanan Anda Sudah diterima', '', 4),
(68, '32641610abea424fbe7870e0c1c65339', '2021-11-29', '11:08:28', 'Pesanan anda sudah diantar Oleh diki', '21313', 5),
(69, '32641610abea424fbe7870e0c1c65339', '2021-11-29', '11:08:48', 'Pesanan Sudah Diterima', '', 6),
(70, '32641610abea424fbe7870e0c1c65339', '2021-11-29', '11:08:59', 'Selesai', '', 7);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_produk`
--

CREATE TABLE `tb_produk` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `harga` int(100) NOT NULL,
  `diskon` int(10) NOT NULL,
  `foto` text NOT NULL,
  `foto2` text NOT NULL,
  `foto3` text NOT NULL,
  `foto4` text NOT NULL,
  `foto5` text NOT NULL,
  `status` int(11) NOT NULL,
  `stok` int(11) NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_produk`
--

INSERT INTO `tb_produk` (`id`, `nama`, `harga`, `diskon`, `foto`, `foto2`, `foto3`, `foto4`, `foto5`, `status`, `stok`, `deskripsi`) VALUES
(21, 'Monascho Fit', 93000, 10, '703ab1e9d7f94d608b139bb06faf9e13.jpg', '', '', '', '', 0, 8, 'Monascho Curcumin dapat meningkatkan daya tahan tubuh lansia pada umur 45 hingga 70 tahun, herbal ini dapat mencegah lansia terserang penyakit-penyakit yang berbahaya'),
(23, 'Monascho Aloe', 433332, 22, 'd71a41b1cc1442acb1e53a7c522b232d.jpg', 'd3cedc13c5d447ca8e5e6a5b2eec6f9d.jpg', '110d3ce692c847cbbc7a47c3d82425e4.jpg', '933f87c01e8b4f96a910b6eb062026d1.jpg', 'd0df4770023742f8a5999894c7b136ed.jpg', 0, 20, 'Monascho Curcumin dapat meningkatkan daya tahan tubuh remaja pada umur 14 hingga 19 tahun, herbal ini dapat mencegah remaja terserang penyakit-penyakit yang berbahaya'),
(24, 'Monascho Curcumin1', 75000, 5, 'b69e3d45b3d0474abc8721496987bf97.jpg', '360cb1ee642a436393388bcb7883e697.jpg', '25fd649e4a134e0b8ac73d417ac6f9d2.jpg', 'bd81c43ffcd34422bf5d0dd2193bbef9.jpg', 'a9c31a773d3f440d9ec6366259480b75.jpg', 0, 8, 'Monascho Curcumin dapat meningkatkan daya tahan tubuh bayi pada umur 1 hingga 4 tahun, herbal ini dapat mencegah bayi terserang penyakit-penyakit yang berbahaya');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_retur`
--

CREATE TABLE `tb_retur` (
  `id` int(11) NOT NULL,
  `id_retur` text NOT NULL,
  `kuantitas_item` int(11) NOT NULL,
  `alasan` text NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `urutan` int(11) NOT NULL,
  `id_transaksi` text NOT NULL,
  `id_konsumen` int(11) NOT NULL,
  `nama_pengirim` varchar(100) NOT NULL,
  `nomor_pengirim` varchar(100) NOT NULL,
  `pembayaran` int(11) NOT NULL COMMENT '1=TF, 2=COD\r\n',
  `lat` varchar(50) NOT NULL,
  `lng` varchar(50) NOT NULL,
  `foto` text NOT NULL,
  `tgl_transaksi` varchar(50) NOT NULL,
  `rating` varchar(10) NOT NULL,
  `komentar` varchar(200) NOT NULL,
  `alamat` text NOT NULL,
  `subtotal` varchar(100) NOT NULL,
  `ongkir` varchar(100) NOT NULL,
  `total` varchar(50) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_transaksi`
--

INSERT INTO `tb_transaksi` (`urutan`, `id_transaksi`, `id_konsumen`, `nama_pengirim`, `nomor_pengirim`, `pembayaran`, `lat`, `lng`, `foto`, `tgl_transaksi`, `rating`, `komentar`, `alamat`, `subtotal`, `ongkir`, `total`, `status`) VALUES
(73, '6d5f008b1089409cbe0e6fe517fcc0b4', 40, '', '', 1, '0.5235568959696282', '123.10578737407921', 'eb2121e846404681bea8afb9de92ff6b.jpg', '2021-10-25 13:39:55', '4.0', 'pengiriman cepat', 'jl trans sulawesi', '226200', '13400', '239600', 7),
(74, '32641610abea424fbe7870e0c1c65339', 41, '', '', 2, '0.5519052553336115', '123.06282307952644', '', '2021-11-29 04:07:55', '4.5', 'bagus', 'alamat', '1013996.88', '2400', '1016396.88', 7);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_administrator`
--
ALTER TABLE `tb_administrator`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_informasi`
--
ALTER TABLE `tb_informasi`
  ADD PRIMARY KEY (`urutan`);

--
-- Indeks untuk tabel `tb_item_transaksi`
--
ALTER TABLE `tb_item_transaksi`
  ADD PRIMARY KEY (`id_item`);

--
-- Indeks untuk tabel `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  ADD PRIMARY KEY (`id_keranjang`);

--
-- Indeks untuk tabel `tb_konsumen`
--
ALTER TABLE `tb_konsumen`
  ADD PRIMARY KEY (`id_konsumen`);

--
-- Indeks untuk tabel `tb_lacakpesanan`
--
ALTER TABLE `tb_lacakpesanan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_produk`
--
ALTER TABLE `tb_produk`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_retur`
--
ALTER TABLE `tb_retur`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`urutan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_administrator`
--
ALTER TABLE `tb_administrator`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `tb_informasi`
--
ALTER TABLE `tb_informasi`
  MODIFY `urutan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `tb_item_transaksi`
--
ALTER TABLE `tb_item_transaksi`
  MODIFY `id_item` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT untuk tabel `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  MODIFY `id_keranjang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT untuk tabel `tb_konsumen`
--
ALTER TABLE `tb_konsumen`
  MODIFY `id_konsumen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT untuk tabel `tb_lacakpesanan`
--
ALTER TABLE `tb_lacakpesanan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT untuk tabel `tb_produk`
--
ALTER TABLE `tb_produk`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT untuk tabel `tb_retur`
--
ALTER TABLE `tb_retur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  MODIFY `urutan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
