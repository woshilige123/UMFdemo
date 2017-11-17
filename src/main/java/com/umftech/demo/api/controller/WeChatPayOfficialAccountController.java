package com.umftech.demo.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umftech.RestReturnTemp;
import com.umftech.util.HttpClientUtil;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;

@RestController
@RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
public class WeChatPayOfficialAccountController {
	
	/**
	 * Call the WeChat Official Pay service to complete payment.
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/getOpenID", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getOpenID(HttpServletRequest req, @RequestBody String reqBody, HttpServletResponse res){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// orderId
		String orderId=""+(Math.round(Math.random()*800000000)+100000)+"";
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    String date = format.format(new Date());
	    orderId = date + orderId;
	    
	    // parameters
		reqMap.put("service", "cb_pre_auth_direct");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("version", "4.0");
		reqMap.put("order_id", orderId);
		reqMap.put("is_wechat_accout", "Y");
		
		String getUrl = "";
		try {
			// use developer kit to get URL
			ReqData reqData = Mer2Plat_v40.makeReqDataByGet(reqMap);
			getUrl = reqData.getUrl();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		rs.setSuccess(true);
		rs.setUrl(getUrl);

		String jsonInString = "{}";
		try {
			jsonInString = mapper.writeValueAsString(rs);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	/**
	 * Call the WeChat Official Pay service to complete payment.
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/createWeChatPayment", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String createWeChatPayment(HttpServletRequest req, @RequestBody String reqBody, HttpServletResponse res){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		Map<String, String> resMap = new HashMap<>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// orderId
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    String merDate = format.format(new Date());
	    String amount = "0.01";
		String goodsData = editGoodsData(reqMap.get("order_id"), amount);
		String merId = reqMap.get("mer_id");
		String cardHolder = reqMap.get("card_holder");
		String identityCode = reqMap.get("identity_code");
	    // parameters
		reqMap.put("service", "cb_active_scancode_pay");
		reqMap.put("charset", "UTF-8");
		reqMap.put("mer_id", merId);
		reqMap.put("ret_url", "http://umpay.huiplus.com.cn/demo/payment_success.jsp");
		reqMap.put("sign_type", "RSA");
		reqMap.put("res_format", "HTML");
		reqMap.put("version", "4.0");
		reqMap.put("mer_date", merDate); 
		reqMap.put("currency", "CNY");
		reqMap.put("user_ip", "10.10.10.10");
		reqMap.put("goods_inf", "test");
		reqMap.put("risk_expand", "A0001:123659973");
		reqMap.put("amount", amount);
		reqMap.put("goods_data", goodsData);
		reqMap.put("pay_type", "WECHAT_OA");
		reqMap.put("card_holder", cardHolder);
		reqMap.put("identity_code", identityCode);
		reqMap.put("identity_type", "IDENTITY_CARD");
		reqMap.put("mobile_id", "15012345678");
		try {
			// get sign
			ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setMsg("welcome");
			Map<String, String> payInfoMap = getPayInfo(resMap.get("pay_info"));
			rs.setAppId(payInfoMap.get("appId"));
			rs.setTimeStamp(payInfoMap.get("timeStamp"));
			rs.setPackageJson(payInfoMap.get("package"));
			rs.setNonceStr(payInfoMap.get("nonceStr"));
			rs.setPaySign(payInfoMap.get("paySign"));
		}else{
			rs.setRetMsg(resMap.get("ret_msg"));
		}
		String jsonInString = "{}";
	
		try {
			jsonInString = mapper.writeValueAsString(rs);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	private String editGoodsData(String orderId, String amount){
		StringBuffer goodsData = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		goodsData.append("<goods_data><sub_order>");
		goodsData.append("<sub_order_id>").append(orderId.substring(4)).append("</sub_order_id>");
		goodsData.append("<sub_order_amt>").append(amount).append("</sub_order_amt>");

		goodsData.append("<sub_trans_code>02223022</sub_trans_code>");

		goodsData.append("</sub_order>"+ "</goods_data>");
		return goodsData.toString();
	}
	
	private Map<String, String> getPayInfo(String payinfo){
		Map<String, String> map = new HashMap<>();
		payinfo = payinfo.replace("{", "");
		payinfo = payinfo.replace("}", "");
		payinfo = payinfo.replace("\"", "");
		String[] payInfoArr = payinfo.split(",");
		for(String str : payInfoArr){
			map.put(str.split(":")[0], str.split(":")[1]);
		}
		return map;
	}
}
