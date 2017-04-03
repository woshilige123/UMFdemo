<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Payment step by step</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" 
    crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script>
		var countdown = 60;
		$(document).ready(function(){
			$("#alerts").hide();
			$("#step2").hide();
		    $("#successful").hide();
			$("#pay").click(function(){
	        	$("#alerts").slideUp();
				$("#mer_id").attr("value", $("#mer_id_input").val());
            	var pageData =  new Object();
			    pageData["amount"] = $("#amount").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    pageData["pay_type"] = $("#pay_type").val();
			    pageData["gate_id"] = $("#gate_id").val();
			    pageData["notify_url"] = $("#notify_url").val();
			    
			    $.ajax("/demo/demo/getTradeNo",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		
			    		if(data.success){
			    			$("#trade_no").attr("value", data.tradeNo);
						    $("#step1").hide();
						    $("#step2").show();
						    if($("#pay_type").val() == "DEBITCARD"){
						    	$("#exp_date").hide();
						    }
			    		}else{
			    			$("#msg").text(data.retMsg);
			    			$("#alerts").show();
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    		alert(data.retMsg);
			    	}
			    });
			});
			
			$("#sendSms").click(function(){
	        	$("#alerts").slideUp();
	        	var pageData =  new Object();
			    pageData["trade_no"] = $("#trade_no").val();
			    pageData["card_id"] = $("#card_id").val();
			    pageData["card_holder"] = $("#card_holder").val();
			    pageData["valid_date"] = $("#valid_date").val();
			    pageData["cvv2"] = $("#cvv2").val();
			    pageData["identity_code"] = $("#identity_code").val();
			    pageData["media_id"] = $("#media_id").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    
			    $.ajax("/demo/demo/sendSms",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
			    			$("#verify_code").prop('disabled', false);
			    			$("#confirm").prop('disabled', false);
			    			$("#sendSms").prop("disabled", true);
			    	        settime(this);
			    		}else{
			    			alert(data.retMsg);
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    		alert(data.retMsg);
			    	}
			    });
			});
			$("#confirm").click(function(){
	        	$("#alerts").slideUp();
	        	var pageData =  new Object();
			    pageData["trade_no"] = $("#trade_no").val();
			    pageData["verify_code"] = $("#verify_code").val();
			    pageData["card_id"] = $("#card_id").val();
			    pageData["card_holder"] = $("#card_holder").val();
			    pageData["valid_date"] = $("#valid_date").val();
			    pageData["cvv2"] = $("#cvv2").val();
			    pageData["identity_code"] = $("#identity_code").val();
			    pageData["media_id"] = $("#media_id").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    
			    $.ajax("/demo/demo/confirmPayment",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
			    			$("#step2").hide();
						    $("#welcome").hide();
						    $("#successful").show();
			    		}else{
			    			$("#msg").text(data.retMsg);
			    			$("#alerts").show();
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    		alert(data.retMsg);
			    	}
			    });

			});
			function settime(val) {
    	        if (countdown == 0) { 
    	        	$("#sendSms").prop("disabled", false);
	    	        $("#sendSms").attr("value", "Send code");
    	        	countdown = 60; 
    	        } else {
	    	        $("#sendSms").prop("disabled", true);
	    	        $("#sendSms").attr("value", "resend(" + countdown + ")");
	    	        countdown--; 
	    	        setTimeout(function() { 
	    	        	settime(val) 
	    	        },1000) 
    	        }
    	    }
			
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
		        $(this).closest('.page-alerts').slideUp();
		    });
		});
		
		
		
	</script>
</head>

<body style="font-family: 'Open Sans', sans-serif;">
<script src='https://js.stripe.com/v2/' type='text/javascript'></script>
	<div class="container">
		<div class="page-alerts" id = "alerts">
			<div class="alert alert-danger page-alert" id="alert999">
		        <button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		        <p id = "msg"></p>
		    </div>
	    </div>
	    <div id = "welcome" class="centered title"><h1>Welcome to our checkout.</h1></div>
		<div id = "successful" class="container">
	        <div class="centered title"><h1>Your payment was completed. </h1>
	            <label class='control-label'>orderID:</label>
                <p id = "orderId"></p>
                <label class='control-label'>tradeNo:</label>
	        	<p id = "tradeNo"></p>
	        </div>
	     </div>
     </div>
     <hr class="featurette-divider"></hr>
     
</body>
</html>