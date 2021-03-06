<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
	<title>getOpenID</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" 
    crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script>
		$(document).ready(function(){
				$("#alerts").hide();
	           	var pageData =  new Object();
	           	var payInfo = new Object();
			    pageData["order_id"] = $("#order_id").val();
			    pageData["open_id"] = $("#open_id").val();
			    $.ajax("/demo/createWeChatPayment",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		
			    		if(data.success){
			    			
			    				
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
     <hr class="featurette-divider"></hr>
     <div class="page-alerts" id = "alerts">
		<div class="alert alert-danger page-alert" id="alert999">
	        <button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
	        <p id = "msg"></p>
	    </div>
    </div>
     <div class="container">
	     <div class="form-group row">
			  <label for="example-url-input" class="col-2 col-form-label">OrderID</label>
			  <div class="col-10">
			 <input class="form-control" type="text" id="open_id" value="<%=request.getParameter("order_id")%>">
			</div>
		 </div>
		 <div class="form-group row">
		     <label for="example-url-input" class="col-2 col-form-label">OrderID</label>
		     <div class="col-10">
		     <input class="form-control" type="text" id="order_id" value="<%=request.getParameter("orderId")%>">
		     </div>
		</div>
	</div>
</body>
</html>