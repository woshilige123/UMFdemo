package com.umftech.demo.api.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class PaymentDemoController {
	@RequestMapping(value = "/getTradeNo", method = RequestMethod.POST)
	@ResponseBody
	public String getTradeNo(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		//Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    String date = format.format(new Date());
	    String orderId=""+(Math.round(Math.random()*800000000)+100000)+"";
	    orderId = date + orderId;

		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			String goodsData = editGoodsData(orderId, (String) map.get("amount"));
			map.put("service", "cb_apply_pay_shortcut");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			map.put("order_id", orderId);
			map.put("mer_date", date);
			map.put("currency", "CNY");
			map.put("goods_data", goodsData);
			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setTradeNo(resMap.get("trade_no"));
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

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public String sendSms(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_sms_req_shortcut");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			// TODO
			map.put("identity_type", "IDENTITY_CARD");
			map.put("media_type", "MOBILE");

			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
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

	@RequestMapping(value = "/confirmPayment", method = RequestMethod.POST)
	@ResponseBody
	public String confirmPayment(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_confirm_pay_shortcut");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			// TODO
			map.put("identity_type", "IDENTITY_CARD");
			map.put("media_type", "MOBILE");

			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setOrderId("201705168545214");
			rs.setSuccess(true);
		}
		rs.setRetMsg(resMap.get("ret_msg"));
		String jsonInString = "{}";

		try {
			jsonInString = mapper.writeValueAsString(rs);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}

	@RequestMapping(value = "/applyRefund", method = RequestMethod.POST)
	@ResponseBody
	public String applyRefund(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();

	    // refundNo
	 	SimpleDateFormat sdf = new  SimpleDateFormat("yyMMddHHmmss");
	 	String refundNo = sdf.format(new Date()).concat(Math.round(Math.random()*8999) + 1000 + "");
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_api_refund");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			map.put("refund_no", refundNo);
			String orderId = (String) map.get("order_id");
			map.put("currency", "CNY");
			// TODO
			StringBuffer refundData = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			refundData.append("<goods_data><sub_order>");
			refundData.append("<sub_order_id>").append(orderId.substring(4)).append("</sub_order_id>");
			refundData.append("<sub_order_type>2</sub_order_type>");
			refundData.append("<sub_refund_amt>").append((String) map.get("refund_amt")).append("</sub_refund_amt>");
			refundData.append("</sub_order>"+ "</goods_data>");
			map.put("refund_data", refundData.toString());

			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setMsg("welcome");
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

	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkStatus(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		String retMsg = "";
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "mer_order_info_query");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "HTML");
			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				retMsg = e.getMessage();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			retMsg = e1.getMessage();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setTradeState(resMap.get("trade_state"));
		}
		rs.setRetMsg("".equals(retMsg) ? resMap.get("ret_msg") : retMsg);
		String jsonInString = "{}";

		try {
			jsonInString = mapper.writeValueAsString(rs);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}

	@RequestMapping(value = "/checkRefundStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkRefundStatus(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_mer_refund_query");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setRefundStatus(resMap.get("refund_state"));
			rs.setCurrency(resMap.get("currency"));
			rs.setRefundAmt(resMap.get("refund_amt"));
			rs.setRefundCnyAmt(resMap.get("refund_cny_amt"));
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

	@RequestMapping(value = "/scancodePay", method = RequestMethod.POST)
	@ResponseBody
	public String scanCode(HttpServletRequest req, @RequestBody String reqBody) throws UnsupportedEncodingException{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();

	    // orderId
		String orderId=""+(Math.round(Math.random()*800000000)+100000)+"";
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    String date = format.format(new Date());
	    orderId = date + orderId;
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_active_scancode_pay");
			map.put("charset", "UTF-8");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");
			map.put("res_format", "STRING");
			//map.put("notify_url", "www.google.com");
			map.put("order_id", orderId);
			map.put("mer_date", date);

			map.put("user_ip", "10.10.10.10");
			map.put("currency", "CNY");
			map.put("goods_inf", "test");
			map.put("risk_expand", "A0001:123659973");
			String goodsData = editGoodsData(orderId, (String) map.get("amount"));
			map.put("goods_data",goodsData);
			map.put("mobile_id", "15011466525");

			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
				resMap = Plat2Mer_v40.getResData(resultString);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setMsg("welcome");
			rs.setPayUrl(resMap.get("bank_payurl"));
			rs.setRetMsg(resMap.get("ret_msg"));
			rs.setMerDate(date);
			rs.setOrderId(orderId);
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
}
