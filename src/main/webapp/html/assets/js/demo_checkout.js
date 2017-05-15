/**
 * 
 */
var step = 1;
var countdown = 60;
$(document).ready(function(){
	$("#CNYprice").addClass("hidden");
	$("#comfirmPay").addClass("hidden");
	
	$("#button-delivery-method").bind("click", function () {
        $("#step2").click();
    });
	$("#button-currency-method").bind("click", function () {
        $("#step3").click();
    	});
    $("#button-payment-method").bind("click", function () {
        $("#step4").click();
    });
    $("#wechat-inApp-web-based").attr("disabled", "true");

	$("#button-payment-next").click(function(){
		var pay_type = $('#pay_type_radio input:radio:checked').val();
		if(pay_type == "UNIONPAY_CARD"){
			if(step==1){
				$("#unionpay_card_info").addClass("hidden");
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-next").attr("value", "Confirm");
				$("#button-payment-next").prop("disabled", true);
				$("#button-payment-pre").removeClass("hidden");
				var targetVal = $("#input-card-no").val();
				if(targetVal.length>=15){
					var cardInfo = getCardInfo(targetVal);
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
				}
				step++;
			}else{
				var pageData =  new Object();
				pageData["trade_no"] = $("#trade_no").val();
				pageData["verify_code"] = $("#input-verify-code").val();
				pageData["card_id"] = $("#card_no_step2").val();
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
							$("#step4").click();
						}else{
						}
					},
					error:function(err){
						console.log(err);
						alert(data.retMsg);
					}
				});
			}
		}else{
			
			var pageData =  new Object();
			pageData["pay_type"] = $('#pay_type_radio input:radio:checked').val()
			pageData["amount"] = 	$("#amount").val();
			pageData["mer_id"] = "8023";
			pageData["card_holder"] = $("#name_app").val();
			pageData["identity_type"] = $("#input-app-id-type").val();
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
						$('#qrcode').qrcode(data.payUrl);
						$("#app_scan_info").addClass("hidden");
						$('#qrcode').removeClass("hidden");
					}else{
					}
				},
				error:function(err){
					console.log(err);
				}
			});
		}
	});

	$("#get_verify_code").click(function(){
		$("#button-payment-next").prop("disabled", false);
		var pageData = new Object();
		pageData["amount"] = $("#amount").val();
        pageData["mer_id"] = "8023";
        pageData["pay_type"] = $("#input-card-type").val();
		pageData["gate_id"] = $("#gate_id").val();
		pageData["notify_url"] = "www.google.com";
		pageData["mer_cust_id"] = $("#mer_cust_id").val();
		pageData["card_id"] = $("#card_no_step2").val();
		pageData["card_holder"] = $("#card_holder").val();
		pageData["valid_date"] = $("#input-expiration-date").val();
		pageData["cvv2"] = $("#input-card-cvv2").val();
        pageData["identity_code"] = $("#input-id").val();
		pageData["media_id"] = $("#input-phone-number").val();
		pageData["media_type"] = "MOBILE";

		$.ajax("/demo/demo/getTradeNo",{
			method:"POST",
			contentType :"application/json",
			data:JSON.stringify(pageData),
			dataType:"json",
			headers:{},
			success:function(data, statusCode){
				if(data.success){
					$("#trade_no").attr("value", data.tradeNo);
					pageData["trade_no"] = data.tradeNo;
					$.ajax("/demo/demo/sendSms", {
						method: "POST",
						contentType: "application/json",
						data: JSON.stringify(pageData),
						dataType: "json",
						headers: {},
						success: function (data, statusCode) {
							if(data.success){
								$("#input-verify-code").prop('disabled', false);
								$("#button-payment-next").prop("disabled", false);
								$("#get_verify_code").prop("disabled", true);
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
		step--;
	});

	$("input[type=radio][name=payment_type]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#unionpay_card_info").addClass("hidden");
		$("#app_scan_info").addClass("hidden");
		if (targetVal == "UNIONPAY_CARD") {
			$("#unionpay_card_info").removeClass("hidden");
			step=1;
        }else {
			$("#app_scan_info").removeClass("hidden");
        }
	});

    $("input[type=radio][name=price]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#CNYprice").addClass("hidden");
        if (targetVal == "CNY") {
			$("#CNYprice").removeClass("hidden");
        }else {
			$("#CNYprice").addClass("hidden");
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
		$("#card_type_step2").text(cardInfo.bankName+cardInfo.cardType);
		$("#card_no_step2").attr("value", $("#input-card-no").val());
	}
}


