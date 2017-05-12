/**
 * 
 */

$(document).ready(function(){
	$(".radio-inline").on("click",function(e){
		var targetVal = $(e.target).val();
		$("#credit_card_info").addClass("hidden");
		$("#debit_card_info").addClass("hidden");
		if(targetVal === "CREDIT_CARD" || targetVal === "DEBIT_CARD"){
			$("#"+targetVal.toLowerCase()+"_info").removeClass("hidden");
		}
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


