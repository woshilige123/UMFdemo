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
			    pageData["identity_code"] = $("#identity_code").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    pageData["card_holder"] = $("#card_holder").val();
			    $("#confirmPayment").click(function(){
			    	$.ajax("/demo/createWeChatPayment",{
				    	method:"POST",
				    	contentType :"application/json",
				    	data:JSON.stringify(pageData),
				    	dataType:"json",
				    	headers:{},
				    	success:function(data, statusCode){
				    		 
				    		if(data.success){
				    			function onBridgeReady(){
				    				
				    				if(data.appId == "" || data.appId == null){
				    					alert("appId can not be empty.");
				    					return;
				    				}
				    				   WeixinJSBridge.invoke(
				    						   'getBrandWCPayRequest', {
				    								"appId":data.appId,
				    								"nonceStr":data.nonceStr,
				    								"package":data.packageJson,
				    								"signType":"MD5",
				    								"timeStamp":data.timeStamp,
				    								"paySign":data.paySign
				    						},
				    				       
				    				       function(res){     
				    				           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				    				        	   //window.location.href= data.url;
				    				           }
				    				       }
				    				   ); 
				    				}
				    				if (typeof WeixinJSBridge == "undefined"){
				    				   if( document.addEventListener ){
				    				       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
				    				   }else if (document.attachEvent){
				    				       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
				    				       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
				    				   }
				    				}else{
				    				   onBridgeReady();
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
			    
			    $("#confirmPayment").click();
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
	        <button type="button" class="close"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>
	        <p id = "msg"></p>
	    </div>
    </div>
     <div class="container">
	     <div class="form-group row">
			  <label for="example-url-input" class="col-2 col-form-label">OpenID</label>
			  <div class="col-10">
			 <input class="form-control" type="text" id="open_id" value="<%=request.getParameter("openid")%>">
			</div>
		 </div>
		 <div class="form-group row">
		     <label for="example-url-input" class="col-2 col-form-label">OrderID</label>
		     <div class="col-10">
		     <input class="form-control" type="text" id="order_id" value="<%=request.getParameter("orderId")%>">
		     </div>
		</div>
		
			<div>
				<input class="btn btn-primary" type="button" value="Confirm Payment" id = "confirmPayment">
	  		</div>
	</div>
	<input type="hidden" name="mer_id" id="mer_id" value="3965"></input>
	<input type="hidden" name="card_holder" id="card_holder" value="李光良"></input>
	<input type="hidden" name="identity_code" id="identity_code" value="222324198408161817"></input>
</body>
</html>