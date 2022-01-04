<?php
function get_data_perbulan($month, $year, $konek)
{
    $sql = "SELECT COUNT(id_transaksi) as y, SUM(total) as t, total FROM tb_transaksi where month(tgl_transaksi) = $month AND year(tgl_transaksi) = $year AND status=7";
    return mysqli_fetch_array(mysqli_query($konek, $sql));
};

$dataPoints = array(
    array("y" => (int) get_data_perbulan('01', date("Y"), $konek)['t'], "label" => "Januari"),
    array("y" => (int) get_data_perbulan('02', date("Y"), $konek)['t'], "label" => "Februai"),
    array("y" => (int) get_data_perbulan('03', date("Y"), $konek)['t'], "label" => "Maret"),
    array("y" => (int) get_data_perbulan('04', date("Y"), $konek)['t'], "label" => "April"),
    array("y" => (int) get_data_perbulan('05', date("Y"), $konek)['t'], "label" => "Mei"),
    array("y" => (int) get_data_perbulan('06', date("Y"), $konek)['t'], "label" => "Juni"),
    array("y" => (int) get_data_perbulan('07', date("Y"), $konek)['t'], "label" => "Juli"),
    array("y" => (int) get_data_perbulan('08', date("Y"), $konek)['t'], "label" => "Agustus"),
    array("y" => (int) get_data_perbulan('09', date("Y"), $konek)['t'], "label" => "September"),
    array("y" => (int) get_data_perbulan('10', date("Y"), $konek)['t'], "label" => "Oktober"),
    array("y" => (int) get_data_perbulan('11', date("Y"), $konek)['t'], "label" => "November"),
    array("y" => (int) get_data_perbulan('12', date("Y"), $konek)['t'], "label" => "Desember"),
);

?>
<div class="page-wrapper">
    <!-- ============================================================== -->
    <!-- Bread crumb and right sidebar toggle -->
    <!-- ============================================================== --> 
    <div class="page-breadcrumb">
        <div class="row">
            <div class="col-12 d-flex no-block align-items-center" >
                <h4 class="page-title">Dashboard</h4>
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
        <!-- Sales Cards  -->
        <!-- ============================================================== -->
        <div class="row" >
            <!-- Column -->
            <div class="col-md-6 col-lg-2 col-xlg-3">
                <a href="?module=dashboard">
                    <div class="card card-hover">
                        <div class="box bg-cyan text-center">
                            <h1 class="font-light text-white"><i class="mdi mdi-view-dashboard"></i></h1>
                            <h6 class="text-white">Dashboard</h6>
                        </div>
                    </div>
                </a>
            </div>
            <!-- Column -->
            <div class="col-md-6 col-lg-2 col-xlg-3">
                <a href="?module=informasi">
                    <div class="card card-hover">
                        <div class="box bg-success text-center">
                            <h1 class="font-light text-white"><i class="mdi mdi-blur-linear"></i></h1>
                            <h6 class="text-white">Informasi</h6>
                        </div>
                    </div>
                </a>
            </div>
            <!-- Column -->
            <div class="col-md-6 col-lg-2 col-xlg-3">
                <a href="?module=produk">
                    <div class="card card-hover">
                        <div class="box bg-warning text-center">
                            <h1 class="font-light text-white"><i class="mdi mdi-chart-bubble"></i></h1>
                            <h6 class="text-white">Produk</h6>
                        </div>
                    </div>
                </a>
            </div>
            <!-- Column -->
            <div class="col-md-6 col-lg-2 col-xlg-3">
                <a href="?module=transaksi">
                    <div class="card card-hover">
                        <div class="box bg-danger text-center">
                            <h1 class="font-light text-white"><i class="mdi mdi-relative-scale"></i></h1>
                            <h6 class="text-white">Pesanan Masuk</h6>
                        </div>
                    </div>
                </a>
            </div>
            <!-- Column -->
            <div class="col-md-6 col-lg-2 col-xlg-3">
                <a href="?module=laporan">
                    <div class="card card-hover">
                        <div class="box bg-info text-center">
                            <h1 class="font-light text-white"><i class="mdi mdi-receipt"></i></h1>
                            <h6 class="text-white">Laporan</h6>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Sales chart -->
        <!-- ============================================================== -->
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div class="d-md-flex align-items-center">
                            <div>
                                <h4 class="card-title">Site Analysis</h4>
                                <!-- <h5 class="card-subtitle">Overview of Latest Month</h5> -->
                            </div>
                        </div>
                        <div class="row" >
                            <!-- column -->
                            <div class="col-lg-12">
                                <div class="flot-chart">
                                    <!-- <div class="flot-chart-content" id="flot-line-chart"></div> -->
                                    <!-- <div id="chartContainer" style="height: 270px; width: 100%;"></div> -->
                                    <div id="chartContainer" style="height: 270px; width: 100%;"></div>
                                </div>
                            </div>

                            <!-- column -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Sales chart -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Recent comment and chats -->
        <!-- ============================================================== -->

        <!-- ============================================================== -->
        <!-- Recent comment and chats -->
        <!-- ============================================================== -->
    </div>
    <br><br><br><br><br><br><br><br><br><br>
</div>

<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script>
    window.onload = function() {

        var chart = new CanvasJS.Chart("chartContainer", {
            title: {
                text: "Tahun <?= date('Y'); ?>"
            },
            axisY: {
                title: "Jumlah Pendapatan"
            },
            data: [{
                type: "line",
                dataPoints: <?php echo json_encode($dataPoints, JSON_NUMERIC_CHECK); ?>
            }]
        });
        chart.render();

    }
</script>