<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Payment Success</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" 
    crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body style="font-family: 'Open Sans', sans-serif;">
<script src='https://js.stripe.com/v2/' type='text/javascript'></script>
	<div class="container">
		<div id = "successful" class="container">
	        <div class="centered title"><h1>Your payment was completed. </h1>
	            <label class='control-label'>orderID:</label>
                <p id = "orderId" value = "<%=request.getParameter("order_id")%>"></p>
	        </div>
	     </div>
     </div>
     <hr class="featurette-divider"></hr>
     
</body>
</html>