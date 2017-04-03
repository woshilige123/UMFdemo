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
	        <div class="centered title"><h1>Your payment was completed. </h1></div>
	     </div>
     </div>
     <hr class="featurette-divider"></hr>
     
	<div id = "step1" class="container">
	        <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Amount</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "amount" value = "0.01">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Merchant ID</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "mer_id_input" value = "8023">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Payment methods</label>
                  <select id="pay_type" class="form-control">
				  	<option value="CREDITCARD" selected="selected">CREDITCARD</option>
				  	<option value="DEBITCARD">DEBITCARD</option>
				  </select>
				  	
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Select Bank</label>
                  <select id="gate_id" class="form-control">
				  	<option value="ICBC">工商银行</option>
					<option value="SPDB">浦发银行</option>	
					<option value="CMBC">民生银行</option>
					<option value="CCB" selected="selected">建设银行</option>	
					<option value="ABC">农业银行</option>	
					<option value="BOC">中国银行</option>	
					<option value="PSBC">邮储银行</option>	
					<option value="COMM">交通银行</option>	
					<option value="CITIC">中信银行</option>	
					<option value="CEB">光大银行</option>	
					<option value="HXB">华夏银行</option>	
					<option value="CMB">招商银行</option>	
					<option value="SHB">上海银行</option>	
					<option value="BJB">北京银行</option>	
					<option value="CIB">兴业银行</option>	
					<option value="NBB">宁波银行</option>	
					<option value="GDB">广发银行</option>	
					<option value="SPAB">平安银行</option>	
					<option value="CZSB">浙商银行</option>
				</select>
				
              </div>
            </div>
            <div class="form-group row">
			  <label for="example-url-input" class="col-2 col-form-label">Notify URL</label>
			  <div class="col-10">
			    <input class="form-control" type="url" value="http://47.88.87.33:8088/demo/notifyWeChatPayResult?server=Oregon" id="notify_url">
			  </div>
			</div>
			
            <div>
  				<input type="button" class="btn btn-info"  id = "pay" value="Continue">
		    </div>
	        </tr>
		</table>
	</div>
	<div id = "step2" class="container">
	        <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Card Number</label>
                <input autocomplete='off' class='form-control card-number' size='20' value = "6259655533117715" type='text' id = "card_id">
              </div>
            </div>
	        <div class='form-row'>
              <div class='form-group required'>
                <div class='error form-group hide'>
	                <div class='alert-danger alert'>
	                  Please correct the errors and try again.
	              
	                </div>
                </div>
                <label class='control-label'>Name on Card</label>
                <input class='form-control' size='4' type='text' value = "罗淳雅" id = "card_holder">
              </div>  
            </div>
            
            <div class='form-row' id = "exp_date">
              <div class='form-group cvc required'>
                <label class='control-label'>cvv2</label>
                <input autocomplete='off' class='form-control card-cvc' placeholder='ex. 311'  value = "358" size='4' type='text' id = "cvv2">
              </div>
              <div class='form-group expiration required' id = "exp_date">
                <label class='control-label'>Expiration date</label>
                <input class='form-control card-expiry-month' placeholder='MMDD'  value = "2011" size='4' type='text' id = "valid_date">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>ID number</label>
                <input autocomplete='off' class='form-control card-number' size='20'  value = "431381198109106573" type='text' id = "identity_code">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Mobile number</label>
                <input autocomplete='off' class='form-control card-number' size='20' type='text' value = "15012345678" id = "media_id">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Enter the code sent to your mobile</label>
                <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "verify_code" disabled="disabled">
              </div>
            </div>
	        <input type = "button" id = "sendSms" value="Send code">
		<div>
			<button class='form-control btn btn-primary submit-button'  id = "confirm" disabled="disabled">Place Order</button>
		</div>
	</div>

	<input type="hidden" name="trade_no" id="trade_no" value=""></input>
	<input type="hidden" name="mer_id" id="mer_id" value=""></input>
</body>
</html>