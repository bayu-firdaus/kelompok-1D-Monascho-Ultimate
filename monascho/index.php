<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <meta name="description" content="">
  <meta name="author" content="">
  <!-- Title -->
  <title>LOGIN | Monascho Ultimate</title>
  <link rel="icon" type="image/png" sizes="16x16" href="admin/public/assets/images/logo.jpg">
  <meta name="msapplication-TileColor" content="#ffffff">
  <meta name="theme-color" content="#ffffff">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <style>
    html,
    body {
      height: 100%;
      margin-top: 30px;
    }

    /* body.my-login-page { 
      background: #eaeaea;
      background-image: url(admin/img/bg_login.png);
      background-size: cover;
      background-repeat: no-repeat;
    } */

    .my-login-page .brand {
      background-color: #fff;
      width: 90px;
      height: 90px;
      overflow: hidden;
      border-radius: 50%;
      margin: 40px auto;
      box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
      position: relative;
      padding: 5%;
      z-index: 1;
    }

    .my-login-page .brand img {
      width: 100%;
    }

    .my-login-page .card-wrapper {
      width: 400px;
    }

    .my-login-page .card {
      border-color: transparent;
      box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
    }

    .my-login-page .card.fat {
      padding: 10px;
    }

    .my-login-page .card .card-title {
      margin-bottom: 30px;
    }

    .my-login-page .form-control {
      border-width: 2.3px;
    }

    .my-login-page .form-group label {
      width: 100%;
    }

    .my-login-page .btn.btn-block {
      padding: 12px 10px;
    }

    .my-login-page .footer {
      margin: 40px 0;
      color: #888;
      text-align: center;
    }

    @media screen and (max-width: 425px) {
      .my-login-page .card-wrapper {
        width: 90%;
        margin: 0 auto;
      }
    }

    @media screen and (max-width: 320px) {
      .my-login-page .card.fat {
        padding: 0;
      }

      .my-login-page .card.fat .card-body {
        padding: 15px;
      }
    }

    #logo-left {
      display: block;
      position: fixed;
      z-index: 1;
      top: 2%;
      left: 2%;
      width: 300px;

      /* Size and position */
      margin: auto;
      padding: 10px;
      /* Styles */
      //background: #fff;
      /* border-radius: 7px;
			border: 2px solid rgba(227, 0, 0, 0.15);
			box-shadow: 0 2px 1px rgba(255, 10, 10, 0.1); */
      cursor: pointer;
      outline: none;
      color: #3e8bf7;

    }
  </style>
</head>

<body class="my-login-page">
  <div id="logo-left">
  </div>
  <section class="h-100">
    <div class="container h-100">
      <div class="row justify-content-md-center h-100">
        <div class="card-wrapper">
          <div class="brand">
            <img src="admin/public/assets/images/logo.jpg" alt="logo">
          </div>
          <p>
        </p>
        <div class="card fat">
          <div class="card-body">
            <h4 class="card-title">Login</h4>
            <form class="form-login" action="cek_login.php" method="post">
              <div class="form-group">
                <label for="email">Username</label>
                <input id="email" type="text" class="form-control" name="username" required autofocus>
                <div class="invalid-feedback">
                  Username is required
                </div>
              </div>

              <div class="form-group">
                <label for="password">Password
                </label>
                <input id="password" type="password" class="form-control" name="password" required data-eye>
                <div class="invalid-feedback">
                  Password is required
                </div>
              </div>

              <div class="form-group m-0">
                <button type="submit" class="btn btn-primary btn-block" style="background-color: #FFDB0A; border: none;">
                  Masuk
                </button>
              </div>
            </form>
          </div>
        </div>
        </div>
      </div>
    </div>
  </section>
  <script async src="https://www.google.com/recaptcha/api.js"></script>
  <ins class="adsbygoogle" data-ad-client="ca-pub-2600957993665650" data-ad-slot="2851708447"></ins>
  <script>
    (adsbygoogle = window.adsbygoogle || []).push({});
  </script>
  <script type="text/javascript" src="admin/public/css/popper-1.14.7.min.js"></script>
  <!-- <script src="js/my-login.js"></script> -->
  <script>
    'use strict';

    $(function() {

      $("input[type='password'][data-eye]").each(function(i) {
        var $this = $(this),
          id = 'eye-password-' + i,
          el = $('#' + id);

        $this.wrap($("<div/>", {
          style: 'position:relative',
          id: id
        }));

        $this.css({
          paddingRight: 60
        });
        $this.after($("<div/>", {
          html: 'Show',
          class: 'btn btn-primary btn-sm',
          id: 'passeye-toggle-' + i,
        }).css({
          position: 'absolute',
          right: 10,
          top: ($this.outerHeight() / 2) - 12,
          padding: '2px 7px',
          fontSize: 12,
          cursor: 'pointer',
        }));

        $this.after($("<input/>", {
          type: 'hidden',
          id: 'passeye-' + i
        }));

        var invalid_feedback = $this.parent().parent().find('.invalid-feedback');

        if (invalid_feedback.length) {
          $this.after(invalid_feedback.clone());
        }

        $this.on("keyup paste", function() {
          $("#passeye-" + i).val($(this).val());
        });
        $("#passeye-toggle-" + i).on("click", function() {
          if ($this.hasClass("show")) {
            $this.attr('type', 'password');
            $this.removeClass("show");
            $(this).removeClass("btn-outline-primary");
          } else {
            $this.attr('type', 'text');
            $this.val($("#passeye-" + i).val());
            $this.addClass("show");
            $(this).addClass("btn-outline-primary");
          }
        });
      });

      $(".my-login-validation").submit(function() {
        var form = $(this);
        if (form[0].checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.addClass('was-validated');
      });
    });
  </script>
</body>
</html>