/**
 * 
 */
var step = 1;
$(document).ready(function(){
	$("#CNYprice").addClass("hidden");
	$("#comfirmPay").addClass("hidden");
	
	$("#button-delivery-method").bind("click", function () {
        $("#step2").click();
        gotoTopOfElement("step1");
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

    $("#submit").bind("click", function () {
        var pageData = new Object();
        var payType = $("input[name='payment']:checked").val();
        pageData["pay_type"] = $("#pay_type").val();
        pageData["amount"] = $("#amount").val();
        pageData["mer_id"] = $("#mer_id").val();
        pageData["card_holder"] = $("#card_holder").val();
        pageData["identity_type"] = $("#identity_type").val();
        pageData["identity_code"] = $("#identity_code").val();
        pageData["notify_url"] = $("#notify_url").val();
        if (payType == "card") {
          $.ajax("/demo/demo/scancodePay", {
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(pageData),
            dataType: "json",
            headers: {},
            success: function (data, statusCode) {
              if (data.success) {
                $('#resMsg').html(data.retMsg);
                $("#welcome").hide();
                $('#code').qrcode(data.payUrl);
              } else {
                $("#msg").text(data.retMsg);
                $("#alerts").show();
              }
            },
            error: function (err) {
              console.log(err);
            }
          });
        } else {
          $.ajax("/demo/demo/scancodePay", {
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(pageData),
            dataType: "json",
            headers: {},
            success: function (data, statusCode) {
              if (data.success) {
                $('#resMsg').html(data.retMsg);
                $('#code').qrcode(data.payUrl);
              } else {
                $("#msg").text(data.retMsg);
              }
            },
            error: function (err) {
              console.log(err);
            }
          });
        }


      });

	$("#button-payment-next").click(function(){
		var pay_type = $('#pay_type_radio input:radio:checked').val();
		if(pay_type == "UNIONPAY_CARD"){
			if(step==1){
				$("#unionpay_card_info").addClass("hidden");
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-next").attr("value", "Confirm");
				$("#button-payment-pre").removeClass("hidden");
				step++;
			}else{
				
			}
		}else{
			$("#app_scan_info").addClass("hidden");
		}
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
		$("#"+targetVal.toLowerCase()+"_info").removeClass("hidden");
	});
	
	var cardPrefix;
	$("#input-card-no").on("keyup",function(e){
		var targetVal = $(e.target).val();
		console.log("change");
		console.log(targetVal);
		if(targetVal.length>=15){
			var cardInfo = getCardInfo(targetVal);
			console.log(cardInfo);
			setBankInfo(cardInfo);
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
	}
}

function gotoTopOfElement(objId){
    var _targetTop = $('#'+objId).offset().top;  
    jQuery("html,body").animate({scrollTop:_targetTop},300);  
}


