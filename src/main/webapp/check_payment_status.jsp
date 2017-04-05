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
			$("#check").click(function(){
	        	$("#alerts").slideUp();
	        	var pageData =  new Object();
	        	if($.trim($("#order_id").val())!=''){
	        		pageData["order_id"] = $("#order_id").val();
	        	}
	        	if($.trim($("#trade_no").val())!=''){
	        		pageData["trade_no"] = $("#trade_no").val();
	        	}
			    pageData["mer_date"] = $("#mer_date").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    
			    $.ajax("/demo/demo/checkStatus",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
							$('#resMsg').html(data.retMsg);
			    			$("#welcome").hide();
			    			$("#step1").hide();
			    		    $("#successful").show();
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
	     <div id = "welcome" class="centered title"><h1>Check your order info here.</h1></div>
	     <div id = "successful" class="container">
	        <div class="centered title"><h1 id = "resMsg"></h1></div>
	     </div>
     </div>
     <hr class="featurette-divider"></hr>
     
	<div id = "step1" class="container">
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Merchant ID</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "mer_id" value = "8023">
              </div>
            </div>
	        <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Order ID</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "order_id">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Trade No.</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "trade_no">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Order data</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "mer_date">
              </div>
            </div>
            <div>
  				<input type="button" class="btn btn-info"  id = "check" value="Check">
		    </div>
	        </tr>
		</table>
	</div>
</body>
</html>