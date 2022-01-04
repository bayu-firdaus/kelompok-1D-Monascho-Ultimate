<?php
include 'inc/cek_session.php';
include 'inc/fungsi_hdt.php';
include 'inc/inc.library.php';
include 'koneksi.php';
?>

<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>
     <meta charset="utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <!-- Tell the browser to be responsive to screen width -->
     <meta name="viewport" content="width=device-width, initial-scale=1">
     <meta name="keywords" content="wrappixel, admin dashboard, html css dashboard, web dashboard, bootstrap 5 admin, bootstrap 5, css3 dashboard, bootstrap 5 dashboard, Matrix lite admin bootstrap 5 dashboard, frontend, responsive bootstrap 5 admin template, Matrix admin lite design, Matrix admin lite dashboard bootstrap 5 dashboard template">
     <meta name="description" content="Matrix Admin Lite Free Version is powerful and clean admin dashboard template, inpired from Bootstrap Framework">
     <meta name="robots" content="noindex,nofollow">
     <title>Monascho Ultimate</title>
     <!-- Favicon icon -->
     <link rel="icon" type="image/png" sizes="16x16" href="public/assets/images/logo.jpg">
     <!-- Custom CSS -->
     <link href="public/assets/libs/flot/css/float-chart.css" rel="stylesheet">
     <!-- Custom CSS -->
     <link href="public/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
     <link href="public/dist/css/style.min.css" rel="stylesheet">
     <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

     <script src="public/sweetalert/jquery.min.js"></script>
     <script src="public/sweetalert/sweetalert.js"></script>
     <link rel="stylesheet" href="public/sweetalert/sweetalert.css" />
     <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.7/dist/jquery.fancybox.min.css" />



</head>

<body>
     <!-- ============================================================== -->
     <!-- Preloader - style you can find in spinners.css -->
     <!-- ============================================================== -->
     <div class="preloader">
          <div class="lds-ripple">
               <div class="lds-pos"></div>
               <div class="lds-pos"></div>
          </div>
     </div>
     <!-- ============================================================== -->
     <!-- Main wrapper - style you can find in pages.scss -->
     <!-- ============================================================== -->
     <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full" data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
          <!-- ============================================================== -->
          <!-- Topbar header - style you can find in pages.scss -->
          <!-- ============================================================== -->
          <header class="topbar" data-navbarbg="skin5">
               <nav class="navbar top-navbar navbar-expand-md navbar-dark">
                    <div class="navbar-header" data-logobg="skin5">

                         <!-- ============================================================== -->
                         <!-- Logo -->
                         <!-- ============================================================== -->
                         <a class="navbar-brand">
                              <!-- Logo icon -->
                              <b class="logo-icon ps-2">
                                   <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                                   <!-- Dark Logo icon -->
                                   <!-- <img src="admin/assets/images/logo-icon.png" alt="homepage" class="light-logo" /> -->

                              </b>
                              <!--End Logo icon -->
                              <!-- Logo text -->
                              <span class="logo-text">
                                   <!-- dark Logo text -->
                                   MONASCHO ULTIMATE

                              </span>
                              <!-- Logo icon -->
                              <!-- <b class="logo-icon"> -->
                              <!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
                              <!-- Dark Logo icon -->
                              <!-- <img src="../../assets/images/logo-text.png" alt="homepage" class="light-logo" /> -->

                              <!-- </b> -->
                              <!--End Logo icon -->
                         </a>
                         <!-- ============================================================== -->
                         <!-- End Logo -->
                         <!-- ============================================================== -->
                         <!-- ============================================================== -->
                         <!-- Toggle which is visible on mobile only -->
                         <!-- ============================================================== -->
                         <a class="nav-toggler waves-effect waves-light d-block d-md-none" href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
                    </div>
                    <!-- ============================================================== -->
                    <!-- End Logo -->
                    <!-- ============================================================== -->
                    <div class="navbar-collapse collapse" data-navbarbg="skin5" style="background:#666666">
                         <!-- ============================================================== -->
                         <!-- toggle and nav items -->
                         <!-- ============================================================== -->
                         <ul class="navbar-nav float-start me-auto" >
                              <li class="nav-item d-none d-lg-block"><a class="nav-link sidebartoggler waves-effect waves-light" href="javascript:void(0)" data-sidebartype="mini-sidebar"><i class="mdi mdi-menu font-24"></i></a></li>
                         </ul>
                         <!-- ============================================================== -->
                         <!-- Right side toggle and nav items -->
                         <!-- ============================================================== -->
                         <ul class="navbar-nav float-end">
                              <!-- ============================================================== -->
                              <!-- Comment -->
                              <!-- ============================================================== -->
                              <li class="nav-item dropdown" >
                                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="mdi mdi-power font-24"></i>
                                   </a>
                                   <ul class="dropdown-menu dropdown-menu-end user-dd animated" aria-labelledby="navbarDropdown">
                                        <a class="dropdown-item" href="../logout.php"><i class="fa fa-power-off me-1 ms-1"></i> Logout</a>
                                   </ul>
                              </li>
                              <!-- ============================================================== -->
                              <!-- User profile and search -->
                              <!-- ============================================================== -->
                         </ul>
                    </div>
               </nav>
          </header>

          <?php
          include "inc/menu.php"
          ?>
          <?php
          include "isi.php"
          ?>

          <!-- ============================================================== -->
          <!-- End Page wrapper  -->
          <!-- ============================================================== -->
     </div>
     <!-- ============================================================== -->
     <!-- End Wrapper -->
     <!-- ============================================================== -->
     <!-- ============================================================== -->
     <!-- All Jquery -->
     <!-- ============================================================== -->

     <script>
          $(document).ready(function() {
               // updating the view with notifications using ajax
               function load_unseen_notification(view = '') {
                    $.ajax({
                         url: "module/transaksi/notif.php",
                         method: "POST",
                         data: {
                              view: view
                         },
                         dataType: "json",
                         success: function(data) {
                              $('.count').html(data.notification);
                              if (data.unseen_notification > 0) {
                                   // badge
                                   $('.count').html(data.unseen_notification);
                              } else {
                                   $('.count').html("");
                              }
                         }
                    });
               }
               load_unseen_notification();

               // load new notifications
               $(document).on('click', '.badge-danger', function() {
                    $('.badge-danger').html('');
                    load_unseen_notification('yes');
               });

               setInterval(function() {
                    load_unseen_notification();;
                    // load_pasien_mendaftar();
               }, 5000);
          });
     </script>

     <script src="public/assets/libs/jquery/dist/jquery.min.js"></script>

     <!-- Bootstrap tether Core JavaScript -->
     <script src="public/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
     <script src="public/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
     <script src="public/assets/extra-libs/sparkline/sparkline.js"></script>
     <!--Wave Effects -->
     <script src="public/dist/js/waves.js"></script>
     <!--Menu sidebar -->
     <script src="public/dist/js/sidebarmenu.js"></script>
     <!--Custom JavaScript -->
     <script src="public/dist/js/custom.min.js"></script>
     <!--This page JavaScript -->
     <!-- <script src="../../dist/js/pages/dashboards/dashboard1.js"></script> -->
     <!-- Charts js Files -->
     <script src="public/assets/libs/flot/excanvas.js"></script>
     <script src="public/assets/libs/flot/jquery.flot.js"></script>
     <script src="public/assets/libs/flot/jquery.flot.pie.js"></script>
     <script src="public/assets/libs/flot/jquery.flot.time.js"></script>
     <script src="public/assets/libs/flot/jquery.flot.stack.js"></script>
     <script src="public/assets/libs/flot/jquery.flot.crosshair.js"></script>
     <script src="public/assets/libs/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
     <script src="public/dist/js/pages/chart/chart-page-init.js"></script>

     <script src="public/assets/extra-libs/DataTables/datatables.min.js"></script>
     <script>
          /****************************************
           *       Basic Table                   *
           ****************************************/
          $('#zero_config').DataTable();
     </script>



</body>

</html>