/**
 * 
 */
var step = 1;
var countdown = 60;
var myTimer;
var sent=0;
$(document).ready(function(){
	$("#step1").click();
	$("#CNYprice").removeClass("hidden")
	$("#USDprice").addClass("hidden");
	$("#comfirmPay").addClass("hidden");
	
	$("#button-delivery-method").bind("click", function () {
        $("#step2").click();
        gotoTopOfElement("step1");
        $("#radio_paytype_usd").attr("checked","checked");
    });
	$("#button-currency-method").bind("click", function () {
        $("#step3").click();
        gotoTopOfElement("step2");
    	});
    $("#button-payment-method").bind("click", function () {
        $("#step4").click();
        gotoTopOfElement("step3");
    });
    
    $("#wechat-inApp-web-based").attr("disabled", "true");
    
    $('#input-card-no').on('keyup mouseout input',function(){
        var $this = $(this),
        v = $this.val();
        /\S{5}/.test(v) && $this.val(v.replace(/\s/g,'').replace(/(.{4})/g, "$1 "));
        
    });
    
	$("#confirm_card_payment").click(function(){
		var pageData =  new Object();
				pageData["trade_no"] = $("#trade_no").val();
				pageData["verify_code"] = $("#input-verify-code").val();
				pageData["card_id"] = removeAllSpace($("#card_no_step2").val());
				pageData["card_holder"] = $("#card_holder").val();
				pageData["valid_date"] = $("#input-expiration-date").val();
				pageData["cvv2"] = $("#input-card-cvv2").val();
				pageData["identity_code"] = $("#input-id").val();
				pageData["media_id"] = $("#input-phone-number").val();
        		pageData["mer_id"] = "8023";
				
				$.ajax("/demo/demo/confirmPayment",{
					method:"POST",
					contentType :"application/json",
					data:JSON.stringify(pageData),
					dataType:"json",
					headers:{},
					success:function(data, statusCode){
						if(data.success){
							$("#confirmation_card").removeClass("hidden");
							window.location = getRootPath() + "/html/payment_success.html?order_id=" + data.orderId;
						}else{
						}
					},
					error:function(err){
						console.log(err);
						alert(data.retMsg);
					}
				});
	});
	$("#button-payment-next").click(function(){
		$('#payinfo_wx').addClass("hidden");

		var pay_type = $('#pay_type_radio input:radio:checked').val();
		if(pay_type == "UNIONPAY_CARD"){
			if(step==1){
				var targetVal = removeAllSpace($("#input-card-no").val())
				var cardInfo = getCardInfo(targetVal);
				if(cardInfo.error_msg){
					$("#unionpay_card_info .form-group").addClass('has-error');
					$("#unionpay_card_info .form-group span").html("Please input a valid bank card number.");
					$("#input-card-no").focus();
					$("#input-card-no").select();
					return;
				}else{
					$("#unionpay_card_info .form-group").removeClass('has-error');
					$("#unionpay_card_info .form-group span").html("You may use this bank card number for demo. 6217580100004222451");
					
				}
				$("#unionpay_card_info").addClass("hidden");
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-next").prop("disabled", true);
				$("#button-payment-pre").removeClass("hidden");
				$("#gate_id").attr("value", cardInfo.bankCode);
				if(cardInfo.cardType == "CC"){
					$("#cvv2").removeClass("hidden");
					$("#expiration_data").removeClass("hidden");
					$("#input-card-type").attr("value", "CREDITCARD");
				}else{
					$("#cvv2").addClass("hidden");
					$("#expiration_data").addClass("hidden");
					$("#input-card-type").attr("value", "DEBITCARD");
				}
				console.log(cardInfo);	
				setBankInfo(cardInfo);
				step++;
			}else{
				$('#payinfo_wx').addClass("hidden");
				$("#paybycard_conformation").removeClass("hidden");
				$("#step4").click();
				gotoTopOfElement("step3");
			}
		}else if(pay_type == "UMFPAY"){
			$("#paybycard_conformation").addClass("hidden");
			var pageData =  new Object();
			pageData["amount"] = $("#amount").val();
			pageData["currency"] = $('#currency_radio input:radio:checked').val();
			pageData["interface_type"] = "01";
			pageData["mer_id"] = "8023";
			pageData["ret_url"] = "https://demo.umftech.com/demo/demo/paymentResult";

			$.ajax("/demo/demo/umfpay",{
				method:"POST",
				contentType :"application/json",
				data:JSON.stringify(pageData),
				dataType:"json",
				headers:{},
				success:function(data, statusCode){
					if(data.success){
						window.location.href= data.url;
					}else{
					}
				},
				error:function(err){
					console.log(err);
				}
			});
			myTimer = setInterval(getPaymentStatus, 1000);
			
		}else{
			$("#paybycard_conformation").addClass("hidden");
			var pageData =  new Object();
			pageData["pay_type"] = $('#pay_type_radio input:radio:checked').val()
			pageData["amount"] = 	$("#amount").val();
			pageData["mer_id"] = "8023";
			pageData["card_holder"] = $("#name_app").val();
			pageData["identity_type"] = "IDENTITY_CARD";
			pageData["identity_code"] = $("#input-id-no").val();
			pageData["notify_url"] = "www.google.com";

			$.ajax("/demo/demo/scancodePay",{
				method:"POST",
				contentType :"application/json",
				data:JSON.stringify(pageData),
				dataType:"json",
				headers:{},
				success:function(data, statusCode){
					if(data.success){
						$('#qrcode').empty();
						$('#qrcode').qrcode(data.payUrl);
						$("#order_id").attr("value", data.orderId);
		    			$("#mer_date").attr("value", data.merDate);
						$("#app_scan_info").addClass("hidden");
						$('#payinfo_wx').removeClass("hidden");
						$("#step4").click();
						gotoTopOfElement("step3");
					}else{
					}
				},
				error:function(err){
					console.log(err);
				}
			});
			myTimer = setInterval(getPaymentStatus, 1000);
		}
	});

	$("#get_verify_code").click(function(){
		sent = 1;
		var pageData = new Object();
		pageData["amount"] = $("#amount").val();
        pageData["mer_id"] = "8023";
        pageData["pay_type"] = $("#input-card-type").val();
		pageData["gate_id"] = $("#gate_id").val();
		pageData["notify_url"] = "www.google.com";
		pageData["mer_cust_id"] = $("#mer_cust_id").val();
		pageData["card_id"] = removeAllSpace($("#card_no_step2").val());
		pageData["card_holder"] = $("#card_holder").val();
		pageData["valid_date"] = $("#input-expiration-date").val();
		pageData["cvv2"] = $("#input-card-cvv2").val();
        pageData["identity_code"] = $("#input-id").val();
		pageData["media_id"] = $("#input-phone-number").val();
		pageData["media_type"] = "MOBILE";

		$.ajax("/restdemo/payments/makePaymentOfBankcard",{
			method:"POST",
			contentType :"application/json",
			data:JSON.stringify(pageData),
			dataType:"json",
			headers:{},
			success:function(data, statusCode){
				if(data.success){
					$("#trade_no").attr("value", data.tradeNo);
					pageData["trade_no"] = data.tradeNo;
					delete pageData["amount"];
					$.ajax("/restdemo/payments/sendVerificationCode", {
						method: "POST",
						contentType: "application/json",
						data: JSON.stringify(pageData),
						dataType: "json",
						headers: {},
						success: function (data, statusCode) {
							if(data.success){
								$("#input-verify-code").prop('disabled', false);
								$("#get_verify_code").prop("disabled", true);
								$("#button-payment-next").prop("disabled", false);
								settime(this);
							}else{
								alert(data.retMsg);
							}
						},
						error: function (err) {
							console.log(err);
						}
					});
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

	$("#button-payment-pre").click(function(){
		$("#unionpay_card_info").removeClass("hidden");
		$("#unionpay_step2").addClass("hidden");
		$("#button-payment-next").attr("value", "Next");
		$("#button-payment-pre").addClass("hidden");
		$("#button-payment-next").prop("disabled", false);
		step--;
	});

	$("input[type=radio][name=payment_type]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#unionpay_card_info").addClass("hidden");
		$("#app_scan_info").addClass("hidden");
		$("#unionpay_step2").addClass("hidden");
		$("#button-payment-next").prop("disabled", false);
		if (targetVal == "UNIONPAY_CARD") {
			if(step==1){
				$("#unionpay_card_info").removeClass("hidden");
				$("#button-payment-next").attr("value", "Next");
			}else{
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-pre").removeClass("hidden");
				if(sent==0){
					$("#button-payment-next").prop("disabled", true);
				}
			}
        }else if(targetVal == "UMFPAY"){
        	$("#app_scan_info").addClass("hidden");
			$("#button-payment-pre").addClass("hidden");
        }
        else {
			$("#app_scan_info").removeClass("hidden");
			$("#button-payment-pre").addClass("hidden");
			$("#button-payment-next").attr("value", "Next");
        }
	});

    $("input[type=radio][name=price]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#CNYprice").addClass("hidden");
		$("#USDprice").addClass("hidden");
        if (targetVal == "CNY") {
			$("#CNYprice").removeClass("hidden");
        }else {
			$("#USDprice").removeClass("hidden");
        }
    });
});

function settime(val) {
    	        if (countdown == 0) { 
    	        	$("#get_verify_code").prop("disabled", false);
	    	        $("#get_verify_code").attr("value", "Send code");
    	        	countdown = 60; 
    	        } else {
	    	        $("#get_verify_code").prop("disabled", true);
	    	        $("#get_verify_code").attr("value", "resend(" + countdown + ")");
	    	        countdown--; 
	    	        setTimeout(function() { 
	    	        	settime(val) 
	    	        },1000) 
    	        }
    	    }

function getCardInfo(cardNum){
	var cardInfo = getBankBin(cardNum);
	if(cardInfo.error_msg){
		console.log(cardInfo.error_msg);
	}else{
		return cardInfo;
	}
	return cardInfo;
}

function setBankInfo(cardInfo){
	if(cardInfo.error_msg){
		$("#err_msg").text(cardInfo.error_msg);
	}else{
		$("#bank_name").text(cardInfo.bankName);
		$("#bank_code").text(cardInfo.bankCode);
		$("#bank_card_type").text(cardInfo.cardType);
		$("#card_type_name").text(cardInfo.cardTypeName);
		$("#card_type_step2").text(cardInfo.bankName+cardInfo.cardTypeName);
		$("#card_no_step2").attr("value", $("#input-card-no").val());
	}
}
function getPaymentStatus(){
	console.log("getPaymentStatus");
	var pageData =  new Object();
	pageData["mer_id"] = "8023";
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
    			window.location = getRootPath() + "/html/payment_success.html?order_id=" + $("#order_id").val();
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

function gotoTopOfElement(objId){
    var _targetTop = $('#'+objId).offset().top;  
    jQuery("html,body").animate({scrollTop:_targetTop},300);  
}

function getRootPath()  
{  
   var pathName = window.location.pathname.substring(1);  
   var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));  
   return window.location.protocol + '//' + window.location.host + '/'+ webName;  
}  

function removeAllSpace(str) {
	return str.replace(/\s+/g, "");
	}
