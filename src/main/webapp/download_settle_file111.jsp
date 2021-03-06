<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Check settlement status</title>
	
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
			    pageData["settle_date"] = $("#settle_date").val();
			    pageData["settle_type"] = $("#settle_type").val();
			    
			    $.ajax("/demo/downloadSettleFile",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
						    $("#step1").hide();
						    $("#step2").show();
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
			    <input class="form-control" type="text" value="3602" id="mer_id">
			  </div>
			</div>
			
			<div class="form-group row">
			  <label for="example-text-input" class="col-2 col-form-label">Settle Date</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="71071814071681201" id="settle_date">
			  </div>
			</div>
			
			<div class="form-group row">
			  <label for="example-text-input" class="col-2 col-form-label">Settle Type</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="20161223" id="settle_type">
			  </div>
			</div>
			
			
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Settle Type</label>
                  <select id="settle_type" class="form-control">
				  	<option value="01" selected="selected">CREDITCARD</option>
				  	<option value="02">DEBITCARD</option>
				  </select>
				  	
              </div>
            </div>
            
			
			<div class="form-group row">
			  <label for="example-text-input" class="col-2 col-form-label">Batch No.</label>
			  <div class="col-10">
			    <input class="form-control" type="text" value="161223R2" id="batch_no">
			  </div>
			</div>
			
	        <div>
				<input class="btn btn-primary" type="button" value="submit" id = "upload">
	  		</div>
	  
		</div>
	</div>
	<div id = "step2" class="container">
		<div class="row text-center">
	        <div class="col-sm-6 col-sm-offset-3">
	        <br><br> <h2 style="color:#0fad00">Success</h2>
	        <img src="http://osmhotels.com//assets/check-true.jpg">
	        <h3>The file information has been submitted.</h3>
	        </div>
		</div>
	</div>
	
</body>


</html>