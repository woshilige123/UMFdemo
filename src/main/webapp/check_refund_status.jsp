<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script>
		$(document).ready(function(){
			$("#step2").hide();
			$("#check").click(function(){
	        	var pageData =  new Object();
			    pageData["refund_no"] = $("#refund_no").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    
			    $.ajax("/demo/demo/checkRefundStatus",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		console.log(data);
			    		console.log(statusCode);
						$("p1").append(data.refundStatus);
						$("p2").append(data.refundAmt);
						$("p3").append(data.currency);
						$("p4").append(data.refundCnyAmt);
					    $("#step1").hide();
					    $("#step2").show();
			    	},
			    	error:function(err){
			    		console.log(err);
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
	        	<th nowrap="nowrap">Refund No</th>
	            <td nowrap="nowrap"><input type="text" id = "refund_no"></td>
	        </tr>
		</table>
		<table>
			<button type = "button" id = "check">Check</button>
		</table>
	</div>
	<div id = "step2">
		<p1>Refund status: </p1>
		<p2>Refund amount: </p2>
		<p3>Currency: </p3>
		<p4>Refund amount(CNY): </p4>
	</div>
</body>
</html>