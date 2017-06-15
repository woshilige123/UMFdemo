<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>UMF Pyament Demo - Order Info Page</title>

    <link href="../html/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="../html/assets/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="../html/assets/css/main.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>

    <!-- Smooth page loading -->
    <div class="loader"></div>
    <!--/ Smooth page loading -->

    <!-- Header -->
    <header>
      <nav class="navbar navbar-default navbar-static-top">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <!-- Logo -->
            <a href="#" class="navbar-brand"><img src="../html/assets/image/logo.png" title="logo" alt="logo" class="img-responsive"></a>
            <!--/ Logo -->
          </div>
        </div>
      </nav>
    </header>
    <!--/ Header -->

    <!-- Sidebar Menu -->

    <!-- Account -->
    <div class="account-box">
      <span class="close-account-box"><i class="material-icons">close</i></span>
      <div class="inner">
        <div class="account">
          <h3>My Account</h3>
          <ul class="list-unstyled">
            <li><a href="#">Login</a></li>
            <li><a href="#">Register</a></li>
          </ul>
        </div>
        <div class="currency">
          <h3>Currency</h3>
          <form method="post" enctype="multipart/form-data" id="form-currency">
            <ul class="list-unstyled">
              <li><a href="#" class="currency-select">USD</a></li>
              <li><a href="#" class="currency-select">EUR</a></li>
              <li><a href="#" class="currency-select">GBP</a></li>
            </ul>
            <input type="hidden" name="code" value="">
            <input type="hidden" name="redirect" value="">
          </form>
        </div>
        <div class="language">
          <h3>Language</h3>
          <form method="post" enctype="multipart/form-data" id="form-language">
            <ul class="list-unstyled">
              <li><a href="#" class="language-select">English</a></li>
              <li><a href="#" class="language-select">Russian</a></li>
            </ul>
            <input type="hidden" name="code" value="">
            <input type="hidden" name="redirect" value="">
          </form>
        </div>
        <div class="telephone">
          <h3>Phone</h3>
          <a href="#">(123) 456 78 90</a>
        </div>
      </div>
    </div>
    <!--/ Account -->

    <!-- Search -->
    <div class="search-box">
      <span class="close-search-box"><i class="material-icons">close</i></span>
      <div class="inner">
        <h3>Search</h3>
        <div id="search" class="input-group input-group-lg">
          <input type="text" name="search" value="" class="form-control">
          <span class="input-group-btn">
            <button type="button" class="btn btn-form btn-block"><i class="material-icons">search</i></button>
          </span>
        </div>
      </div>
    </div>
    <!--/ Search -->

    <!--/ Sidebar Menu -->

    <!-- Container -->
    <div class="container-fluid">
      <div class="row">

        <!-- Content -->
        <div id="content" class="col-sm-9 space-30 bg-filled">

          <h1>Thank you for your order.</h1>
          <table class="table">
            <thead>
              <tr>
                <td class="text-left" colspan="2">Order Details</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="text-left" style="width: 50%;">
                  <b>Order ID:</b> <span id="order_id_text">${trade_no}</span><br />
                  <b>Date Added:</b> ${pay_date}
                </td>
                <td class="text-left" style="width: 50%;">
                  <b>Payment Method:</b> Visa Card ending in ${last_four_cardid}<br />
                  <b>Shipping Method:</b> Flat Shipping Rate
                </td>
              </tr>
            </tbody>
          </table>
          <hr>
          <table class="table">
            <thead>
              <tr>
                <td class="text-left" style="width: 50%; vertical-align: top;">Payment Address</td>
                <td class="text-left" style="width: 50%; vertical-align: top;">Shipping Address</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="text-left">
                  Peter Pan<br />
                  15 N Ellsworth Ave<br />
                  San Mateo<br />
                  CA 94401<br />
                  United States<br />
                </td>
                <td class="text-left">
                  Peter Pan<br />
                  15 N Ellsworth Ave<br />
                  San Mateo<br />
                  CA 94401<br />
                  United States<br />
                </td>
              </tr>
            </tbody>
          </table>
          <hr>
          <div class="buttons clearfix">
            <div class="pull-right"><a href="#" class="btn btn-primary" id="continue">Continue Shopping</a></div>
          </div>

        </div>
        <!--/ Content -->

      </div>
    </div>
    <!--/ Container -->

    <!-- Footer -->
    <footer>
      <div class="container-fluid space-30">
        <div class="row">
          <div class="col-sm-2">
            <h5>Information</h5>
            <ul class="list-unstyled">
              <li><a href="#">About Us</a></li>
              <li><a href="#">Delivery Information</a></li>
              <li><a href="#">Privacy Policy</a></li>
              <li><a href="#">Terms & Conditions</a></li>
            </ul>
          </div>
          <div class="col-sm-2">
            <h5>Customer Service</h5>
            <ul class="list-unstyled">
              <li><a href="#">Contact Us</a></li>
              <li><a href="#">Returns</a></li>
              <li><a href="#">Site Map</a></li>
            </ul>
          </div>
          <div class="col-sm-2">
            <h5>Extras</h5>
            <ul class="list-unstyled">
              <li><a href="#">Brands</a></li>
              <li><a href="#">Gift Certificates</a></li>
              <li><a href="#">Affiliates</a></li>
              <li><a href="#">Specials</a></li>
            </ul>
          </div>
          <div class="col-sm-2">
            <h5>My Account</h5>
            <ul class="list-unstyled">
              <li><a href="#">My Account</a></li>
              <li><a href="#">Order History</a></li>
              <li><a href="#">Wishlist</a></li>
              <li><a href="#">Newsletter</a></li>
            </ul>
          </div>
          <div class="col-sm-2">
            <h5>Contact Us</h5>
            <ul class="list-unstyled">
              <li>(123) 456 78 90</li>
              <li>solve@themefiber.com</li>
            </ul>
          </div>
          <div class="col-sm-2">
            <h5>Social</h5>
            <ul class="list-inline">
              <li><a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
              <li><a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a></li>
              <li><a href="#"><i class="fa fa-instagram" aria-hidden="true"></i></a></li>
              <li><a href="#"><i class="fa fa-skype" aria-hidden="true"></i></a></li>
              <li><a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a></li>
              <li><a href="#"><i class="fa fa-tumblr" aria-hidden="true"></i></a></li>
            </ul>
          </div>
        </div>
        <div class="row">
          <div class="col-xs-12">
            <div class="powered text-right">
              <p>Desing by <a href="http://themefiber.com/">Themefiber</a></p>
            </div>
          </div>
        </div>
      </div>
    </footer>
    <!--/ Footer -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../html/assets/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../html/assets/js/bootstrap.min.js"></script>
    <script src="../html/assets/js/common.js"></script>
    <script src="../html/assets/js/demo_success.js"></script>
    <script>
    
    $("#continue").bind("click", function () {
        var pathName = window.location.pathname.substring(1);  
        var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));  
    	window.location = window.location.protocol + '//' + window.location.host + '/'+ webName + "/html/";
    });
    </script>
        
    
  </body>
</html>
