<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Download Settle File</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script>
		$(document).ready(function(){
			
			$("#check").click(function(){
	        	var pageData =  new Object();
			    pageData["settle_date"] = $("#settle_date").val();
			    pageData["settle_type"] = $("#settle_type").val();
			    
			    $.ajax("/demo/downloadSettleFile",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		console.log(data);
			    		console.log(statusCode);
			    		alert(data.retMsg);
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
	<form name="subForm" id="subForm" action="" method="post">
		<div id = "step1">
			<table>
				<tr>
		        	<th nowrap="nowrap">Merchant ID</th>
		            <td nowrap="nowrap"><input type="text" name = "mer_id" id = "mer_id"></td>
		        </tr>
		        <tr>
		        	<th nowrap="nowrap">Settle data</th>
		            <td nowrap="nowrap"><input type="text" name = "settle_date" id = "settle_date"></td>
		        </tr>
			</table>
			<table>
				<button type = "button" id = "check">Check</button>
			</table>
		</div>
	</form>
	
</body>
</html>