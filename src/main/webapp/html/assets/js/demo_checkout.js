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
});

