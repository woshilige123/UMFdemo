<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Scan QR code</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.qrcode.min.js"></script>
	<script>
		$(document).ready(function(){

			$("#submit").click(function(){
	        	var pageData =  new Object();
			    pageData["pay_type"] = $("#pay_type").val();
			    pageData["amount"] = $("#amount").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    pageData["card_holder"] = $("#card_holder").val();
			    pageData["identity_type"] = $("#identity_type").val();
			    pageData["identity_code"] = $("#identity_code").val();
			    pageData["notify_url"] = $("#notify_url").val();

			    $.ajax("/demo/demo/scancodePay",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
			    			console.log(data);
				    		console.log(statusCode);
				    		alert(data.retMsg);
				    		$('#code').qrcode(data.payUrl);
			    		}else{
			    			console.log(err);
				    		alert(data.retMsg);
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    		alert(data.retMsg);
			    	}
			    });

			});
		});

	</script>
	<style type="text/css">
		#step1{
			margin-top: 200px;
    		margin-left: 200px;
		}
		#code{
			margin-top: 100px;
    		margin-left: 200px;
		}
	</style>
</head>

<body>
	<div id = "step1">
		<table>
			<tr>
	        <th nowrap="nowrap">Merchant ID</th>
	        <td nowrap="nowrap"><input type="text" id = "mer_id"></td>
	    </tr>
			<tr>
	        <th nowrap="nowrap">Amount</th>
	        <td nowrap="nowrap"><input type="text" id = "amount"></td>
	    </tr>
	    <tr>
	        <th nowrap="nowrap">Payment type</th>
	        <td nowrap="nowrap"><select id="pay_type">
				  	<option value="WECHAT" selected="selected">微信</option>
				  	<option value="ALIPAY">支付宝</option>
					</td>
	    </tr>
			<tr>
	        	<th nowrap="nowrap">Full name</th>
	            <td nowrap="nowrap"><input type="text" id = "card_holder"></td>
	        </tr>
			<tr>
	        	<th nowrap="nowrap">ID type</th>
	            <td nowrap="nowrap"><select id="identity_type">
				  <option value="IDENTITY_CARD" selected="selected">身份证</option>
				</td>
	        </tr>
			<tr>
	        	<th nowrap="nowrap">ID</th>
	            <td nowrap="nowrap"><input type="text" id = "identity_code"></td>
	        </tr>
			<tr>
	        	<th nowrap="nowrap">Notify URL</th>
	            <td nowrap="nowrap"><input type="text" id = "notify_url" value = "http://47.88.87.33:8088/demo/demo/notifyResult?"></td>
	        </tr>

		</table>
		<table>
			<button type = "button" id = "submit">General QR code</button>
		</table>
	</div>
	<div id="code"></div>
</body>
</html>
