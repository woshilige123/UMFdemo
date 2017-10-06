<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Check order information</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script>
		$(document).ready(function(){
		    $("#successful").hide();
			$("#alerts").hide();
			$('#sign').val('');
			$("#check").click(function(){
	        	$("#alerts").slideUp();
	        	var pageData =  new Object();
			    pageData["plain"] = $("#plain").val();
			    pageData["merID"] = $("#mer_id").val();
			    
			    $.ajax("/demo/demo/generateSign",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.sign){
							$('#sign').val(data.sign);
			    		}else{
			    			$("#msg").text(data.retMsg);
			    			$("#alerts").show();
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
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
		        $(this).closest('.page-alerts').slideUp();
		    });
		    
		});
		
		
	</script>
</head>

<body>
	<div class="container">
		<div class="page-alerts" id = "alerts">
			<div class="alert alert-danger page-alert" id="alert999">
		        <button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		        <p id = "msg"></p>
		    </div>
	    </div>
	     <div id = "welcome" class="centered title"><h1>Generate Signature</h1></div>
	     <div id = "successful" class="container">
	        <div class="centered title"><h1 id = "resMsg"></h1></div>
	     </div>
     </div>
     <hr class="featurette-divider"></hr>
     
	<div id = "step1" class="container">
		<div class='form-row'>
			<div class='form-group card required'>
		         <label class='control-label'>Merchant ID</label>
		         <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "mer_id" value = "">
			</div>
		</div>
		<div class='form-group'>
			<div class='form-group card required'>
				<label for="comment">Data:</label>
				<textarea class="form-control" rows="5" id="plain"></textarea>
				<label for="comment">Sign:</label>
				<textarea class="form-control" rows="5" id="sign"></textarea>
			</div>
		</div>
		<div>
			<input type="button" class="btn btn-info"  id = "check" value="Generate">
		</div>
	</div>
</body>
</html>