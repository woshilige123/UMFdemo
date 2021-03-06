<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Mobile pay</title>
	<script
	src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.qrcode.min.js"></script>
	    <style>
        #main {
            margin-top: 200px;
        }
        
        .modal {
		    display:    none;
		    position:   fixed;
		    z-index:    1000;
		    top:        0;
		    left:       0;
		    height:     100%;
		    width:      100%;
		    background: rgba( 255, 255, 255, .8 ) 
		                url('http://i.stack.imgur.com/FhHRx.gif') 
		                50% 50% 
		                no-repeat;
		}
		
		/* When the body has the loading class, we turn
		   the scrollbar off with overflow:hidden */
		body.loading {
		    overflow: hidden;   
		}
		
		/* Anytime the body has the loading class, our
		   modal element will be visible */
		body.loading .modal {
		    display: block;
		}

    </style>
	<script>
	
	var myTimer;
		$(document).ready(function(){
			$("#alerts").hide();
			$("#submit").click(function(){
	        	$("#alerts").slideUp();
	        	$('#code').empty();
            	$("body").addClass("loading");
	        	var pageData =  new Object();
			    pageData["pay_type"] = $("#pay_type").val();
			    pageData["amount"] = $("#amount").val();
			    pageData["mer_id"] = $("#mer_id").val();
			    pageData["card_holder"] = $("#card_holder").val();
			    pageData["identity_type"] = $("#identity_type").val();
			    pageData["identity_code"] = $("#identity_code").val();
			    pageData["notify_url"] = $("#notify_url").val();

			    $.ajax("/demo/demo/scancodePay",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		if(data.success){
							$('#resMsg').html(data.retMsg);
			    			$("#welcome").hide();
			    			$('#code').qrcode(data.payUrl);
			    			$('#code').goTo();
			    			$("#order_id").attr("value", data.orderId);
			    			$("#mer_date").attr("value", data.merDate);
			    			//myTimer = setTimeout("setInterval(getPaymentStatus, 1000)",5000);
			    			myTimer = setInterval(getPaymentStatus, 1000);
			    		}else{
			    			$("#msg").text(data.retMsg);
			    			$("#alerts").show();
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    	},
                    complete: function(){
                    	$("body").removeClass("loading");
                    }
			    });
			});
			
			function getPaymentStatus(){
				console.log("getPaymentStatus");
	        	var pageData =  new Object();
	        	pageData["mer_id"] = $("#mer_id").val();
	        	pageData["order_id"] = $("#order_id").val();
	        	pageData["mer_date"] = $("#mer_date").val();
	        	pageData["order_type"] = "1";
				$.ajax("/demo/demo/checkStatus",{
			    	method:"POST",
			    	contentType :"application/json",
			    	data:JSON.stringify(pageData),
			    	dataType:"json",
			    	headers:{},
			    	success:function(data, statusCode){
			    		//console.log(data);
			    		if(data.tradeState === 'TRADE_SUCCESS'){
			    			//stopTimer  myTimer.stop();
			    			window.clearInterval(myTimer);
			    			//redirect to success url.
    						window.location = "${pageContext.request.contextPath }/payment_success.jsp?order_id=" + $("#order_id").val();
			    			//window.location = "www.google.com";
			    		}
			    	},
			    	error:function(err){
			    		console.log(err);
			    		//myTimer.stop();
			    		window.clearInterval(myTimer);
			    	}
			    });
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
		
		(function($) {
		    $.fn.goTo = function() {
		        $('html, body').animate({
		            scrollTop: $(this).offset().top + 'px'
		        }, 'fast');
		        return this; // for chaining...
		    }
		})(jQuery);
	</script>
</head>

<body>
	<div class="modal">
	</div>
	<div class="container">
		<div class="page-alerts" id = "alerts">
			<div class="alert alert-danger page-alert" id="alert999">
		        <button type="button" class="close"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		        <p id = "msg"></p>
		    </div>
	    </div>
	     <div id = "welcome" class="centered title"><h1>Type in your payment type here.</h1></div>
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
                  <label class='control-label'>Amount</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "amount" value = "0.01">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Payment type</label>
                  <select id="pay_type" class="form-control">
				  	<option value="WECHAT" selected="selected">WECHAT</option>
				  	<option value="WECHAT_OA">WECHAT_OA</option>
				  	<option value="WECHAT_APP">WECHAT_APP</option>
				  	<option value="ALIPAY">ALIPAY</option>
				  </select>
				  	
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Full name</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "card_holder" value = "罗淳雅">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>ID type</label>
                  <select id="identity_type" class="form-control">
				  	<option value="IDENTITY_CARD" selected="selected">IDENTITY_CARD</option>
				  </select>
				  	
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>ID</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "identity_code" value = "431381198109106573">
              </div>
            </div>
            <div class='form-row'>
              <div class='form-group card required'>
                  <label class='control-label'>Notify URL</label>
                  <input autocomplete='off' class='form-control card-number' size='20' type='text' id = "notify_url" value = "https://dev.demo.umftech.com/demo/demo/notifyResult?">
              </div>
            </div>
            <div class='form-row'>
	            <div class='form-group card required'>
	  				<input type="button" class="btn btn-info"  id = "submit" value="General QR code">
			    </div>
		    </div>
	        </tr>
		</table>
	</div>
	
	<div id="code" style="position:absolute; top:600px; left:400px; z-index:2"></div>
	<input type="hidden" name="order_id" id="order_id" value=""></input>
	<input type="hidden" name="mer_date" id="mer_date" value=""></input>
</body>
</html>
