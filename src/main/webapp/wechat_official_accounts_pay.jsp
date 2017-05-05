<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Official accounts pay</title>
	
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" 
    crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/alert.css">
	
	<script>
		$(document).ready(function(){
		    $("#alerts").hide();
		    $("#step2").hide();
			$("#upload").click(function(){
	        	var pageData =  new Object();
	        	$("#alerts").hide();
			    pageData["mer_id"] = $("#mer_id").val();
			    //pageData["amount"] = $("#amount").val();
			    //pageData["goods_inf"] = $("#goods_inf").val();
			    //pageData["ret_url"] = $("#ret_url").val();
			    pageData["notify_url"] = $("#notify_url").val();
			    
			    $.ajax("/demo/getOpenID",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
			    			window.location.href= data.url;
			    		}else{
			    			$("#msg").text(data.retMsg);
			    			$("#alerts").show();
			    			//alertMessage(data.retMsg);
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
		    			$("#msg").text(data.retMsg);
		    			$("#alerts").show();
			    	}
			    });

			});
			
		    //Show alert
		    function alertMessage(message) {
		        var timeOut;
		        $("#alert999").slideDown();
		        
		        //Is autoclosing alert
		        var delay = $(this).attr('data-delay');
		        if(delay != undefined)
		        {
		            delay = parseInt(delay);
		            clearTimeout(timeOut);
		            timeOut = window.setTimeout(function() {
		                    alert.slideUp();
		                }, delay);
		        }
		    }
		    //Close alert
		    $('.page-alert .close').click(function(e) {
		        e.preventDefault();
		        $(this).closest('.page-alert').slideUp();
		    });
		});
	</script>
</head>
<body style="font-family: 'Open Sans', sans-serif;">
<script src='https://js.stripe.com/v2/' type='text/javascript'></script>
	<div class="page-alerts" id = "alerts">
		<div class="alert alert-danger page-alert" id="alert999">
	        <button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
	        <p id = "msg"></p>
	    </div>
    </div>
	<div id = "step1" >
		<div class="container">
		     <div id = "welcome" class="centered title"><h1>Please enter the information below.</h1></div>
		     
		</div>
	    <hr class="featurette-divider"></hr>
	     
		<div class="container">
			<div class="form-group row">
			  <label for="example-text-input" class="col-2 col-form-label">Merchant ID</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="8023" id="mer_id">
			  </div>
			</div>
			
			<div class="form-group row">
			  <label for="example-text-input" class="col-2 col-form-label">Amount</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="1" id="amount">
			  </div>
			</div>
			
			<div class="form-group row">
			<label class='control-label'>Payment methods</label>
                  <select id="is_public_number" class="form-control">
				  	<option value="Y" selected="selected">Official account</option>
				  	<option value="N">Scan code</option>
				  </select>
				  
			  <label for="example-text-input" class="col-2 col-form-label">Item information</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="Kindle Paperwhite E-reader" id="goods_inf">
			  </div>
			</div>
			
			<div class="form-group row">
			  <label for="example-url-input" class="col-2 col-form-label">Notify URL</label>
			  <div class="col-10">
			    <input class="form-control" type="url" value="http://umpay.huiplus.com.cn:8088/demo/officialAccount.jsp" id="notify_url">
			  </div>
			</div>
			
	        <div>
				<input class="btn btn-primary" type="button" value="submit" id = "upload">
	  		</div>
	  
		</div>
	</div>
	
</body>


</html>