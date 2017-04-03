<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="java.text.*" %>   
<%@ page import="java.util.*" %> 
<%@ page import="java.net.URLEncoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
	<head>
		<title>Shopping Cart</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
		<script
		src="https://code.jquery.com/jquery-3.1.1.js"
		integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
		crossorigin="anonymous"></script>
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
			
	    <script
				  src="https://code.jquery.com/jquery-3.1.1.js"
				  integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
				  crossorigin="anonymous"></script>
	</head>
		<%
		    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String date = format.format(new Date());
			String orderId=date+(Math.round(Math.random()*800000)+100000)+"";
			String subOrderId = orderId.substring(4);

		%>
		<script type="text/javascript">                            
            function getSignByPost(){
            	var pageData =  new Object();
            	var amount = document.getElementById("amount").value;
			    pageData["service"] = $("#service").val(); 
			    pageData["amount"] = $("#amount").val(); 
			    pageData["charset"] = $("#charset").val(); 
			    pageData["mer_id"] = $("#mer_id").val(); 
			    pageData["sign_type"] = $("#sign_type").val(); 
			    pageData["res_format"] = $("#res_format").val(); 
			    pageData["order_id"] = $("#order_id").val(); 
			    pageData["mer_date"] = $("#mer_date").val(); 
			    pageData["mer_priv"] = $("#mer_priv").val(); 
			    pageData["goods_inf"] = $("#goods_inf").val(); 
			    pageData["interface_type"] = $("#interface_type").val(); 
			    pageData["version"] = $("#version").val(); 
			    pageData["currency"] = $("#currency").val();
			    pageData["notify_url"] = $("#notify_url").val(); 

			    var str1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><goods_data><sub_order><sub_order_id>";
			    var str2 = "</sub_order_id><sub_order_amt>".concat(amount).concat("</sub_order_amt><sub_trans_code>02000000</sub_trans_code></sub_order></goods_data>");
				var goods_data = str1.concat("<%=subOrderId%>").concat(str2);
			    pageData["goods_data"] = goods_data; 
			    
			    document.getElementById("goods_data").value = goods_data;
			    
			    $.ajax("/demo/demo/getSign",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		console.log(data);
			    		console.log(statusCode);
			    		$("#subForm").attr("action", data.url);
			    		submitForm(data.sign);
			    	},
			    	error:function(err){
			    		console.log(err);
			    	}
			    	
			    });
	

            };
            
            function submitForm(sign){
                document.getElementById("sign").value = sign;
				document.getElementById("subForm").submit();
            }
    	</script> 

	<body>
        <form name="subForm" id="subForm" action="" method="post">
		<nav class="navbar">
			<div class="container">
				<a class="navbar-brand" href="#">Your online store</a>
				<div class="navbar-right">
					<div class="container minicart"></div>
				</div>
			</div>
		</nav>
		
		<div class="container-fluid breadcrumbBox text-center">
			<ol class="breadcrumb">
				<li><a href="#">Review</a></li>
				<li class="active"><a href="#">Order</a></li>
				<li><a href="#">Payment</a></li>
			</ol>
		</div>
		
		<div class="container text-center">

			<div class="col-md-5 col-sm-12">
				<div class="bigcart"></div>
				<h1>Your shopping cart</h1>
				
			</div>
			
			<div class="col-md-7 col-sm-12 text-left">
				<ul>
					<li class="row list-inline columnCaptions">
						<span>QTY</span>
						<span>ITEM</span>
						<span>Price</span>
					</li>
					<li class="row">
						<span class="quantity">
							<select>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
						</span>
							
						<span class="itemName">Birthday Cake</span>
						<span class="popbtn"><a class="arrow"></a></span>
						<span class="price">$49.95</span>
					</li>
					<li class="row">
						<span class="quantity">50</span>
						<span class="itemName">Party Cups</span>
						<span class="popbtn"><a class="arrow"></a></span>
						<span class="price">$5.00</span>
					</li>
					<li class="row">
						<span class="quantity">20</span>
						<span class="itemName">Beer kegs</span>
						<span class="popbtn"><a class="arrow"></a></span>
						<span class="price">$919.99</span>				
					</li>
					<li class="row">
						<span class="quantity">18</span>
						<span class="itemName">Pound of beef</span>
						<span class="popbtn"><a class="arrow"></a></span>
						<span class="price">$267.45</span>
					</li>
					<li class="row">
						<span class="quantity">1</span>
						<span class="itemName">Bullet-proof vest</span>
						<span class="popbtn"  data-parent="#asd" data-toggle="collapse" data-target="#demo"><a class="arrow"></a></span>
						<span class="price">$450.00</span>				
					</li>
					<li class="row totals">
						<span class="itemName">Total:</span>
						<input type="hidden" value="1694.43" name="amount" id="amount"/> <span class="price">$1694.43</span>
						<span class="price"><input value="1694.43" id="amount" name="amount"/></span>
					</li>
					
					
			        	<span class="itemName">Mer_ID:</span>
			        	<input type="text" name="mer_id" id = "mer_id">
						<span class="order"><a type="submit" class="text-center" href="javascript:void(0)" onclick="javascript:getSignByPost(this)">Checkout</a></span>
				</ul>
			</div>
			<input type="hidden" name="service" id="service" value="cross_border_pay"></input>
			<input type="hidden" name="charset" id="charset" value="UTF-8"></input>
			<input type="hidden" name="sign_type" id="sign_type" value="RSA"></input>
			<input type="hidden" name="sign" id="sign" value=""></input>	
			<input type="hidden" name="res_format" id="res_format" value="HTML"></input>
			<input type="hidden" name="order_id" id="order_id" value="<%=orderId%>"></input>	
			<input type="hidden" name="mer_date" id="mer_date" value="<%=date%>"></input>
			<input type="hidden" name="mer_priv" id="mer_priv" value="cross_border_demo"></input>
			<input type="hidden" name="goods_inf" id="goods_inf" value="demo"></input>			
			<input type="hidden" name="interface_type" id="interface_type" value="01"></input>
			<input type="hidden" name="version" id="version" value="4.0"></input>
			<input type="hidden" name="currency" id="currency" value="USD"></input>
			<input type="hidden" name="goods_data" id="goods_data" value=""></input>
			<input type="hidden" name="notify_url" id="notify_url" value="http://47.88.87.33:8088/demo/demo/notifyResult?"></input>
			

		</div>
		</form>
		<!-- The popover content -->

		<div id="popover" style="display: none">
			<a href="#"><span class="glyphicon glyphicon-pencil"></span></a>
			<a href="#"><span class="glyphicon glyphicon-remove"></span></a>
		</div>
		
		<!-- JavaScript includes -->
<!--
		<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script> 
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/customjs.js"></script>
		-->

	</body>
</html>