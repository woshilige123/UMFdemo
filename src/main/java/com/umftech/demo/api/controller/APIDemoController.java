package com.umftech.demo.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
import com.umpay.api.exception.VerifyException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class APIDemoController {
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
			map.put("trade_no", (String) map.get("trade_no"));
			map.put("media_id", (String) map.get("media_id"));
			// TODO
			map.put("identity_type", "IDENTITY_CARD");
			map.put("media_type", "MOBILE");

			map.put("valid_date", (String) map.get("valid_date"));
			map.put("cvv2", (String) map.get("cvv2"));
			map.put("card_id", (String) map.get("card_id"));
			map.put("card_holder", (String) map.get("card_holder"));
			map.put("identity_code", (String) map.get("identity_code"));

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
			map.put("trade_no", (String) map.get("trade_no"));
			map.put("verify_code", (String) map.get("verify_code"));
			map.put("media_id", (String) map.get("media_id"));
			map.put("media_type", "MOBILE");
			map.put("card_id", (String) map.get("card_id"));

			map.put("valid_date", (String) map.get("valid_date"));
			map.put("cvv2", (String) map.get("cvv2"));
			map.put("card_holder", (String) map.get("card_holder"));
			// TODO
			map.put("identity_type", "IDENTITY_CARD");
			map.put("identity_code", (String) map.get("identity_code"));

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
			map.put("order_id", orderId);
			map.put("mer_date", (String) map.get("mer_date"));

			map.put("refund_amt", (String) map.get("refund_amt"));
			map.put("org_amount", (String) map.get("org_amount"));
			map.put("currency", "CNY");
			// TODO
			StringBuffer refundData = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			refundData.append("<goods_data><sub_order>");
			refundData.append("<sub_order_id>").append(orderId.substring(4).concat("1")).append("</sub_order_id>");
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
			map.put("order_type", "2");
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



	@RequestMapping(value = "/checkCustomStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkCustomStatus(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_update_customs_quire");
			map.put("sign_type", "RSA");
			map.put("charset", "UTF-8");
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
			rs.setCustomStatus(resMap.get("status"));
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


	@RequestMapping(value = "/getBankList", method = RequestMethod.POST)
	@ResponseBody
	public String getBankList(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "cb_get_mer_bank_shortcut");
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
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("bank_list", resMap.get("mer_bank_list").split("\\|"));
			rs.setInfo(retMap);
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


		@RequestMapping(value = "/updateCustomInfo", method = RequestMethod.POST)
		@ResponseBody
		public String updateCustomInfo(HttpServletRequest req, @RequestBody String reqBody) throws UnsupportedEncodingException{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> resMap = new HashMap<>();

			try {
				map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
				map.put("service", "cb_update_customs_info");
				map.put("charset", "UTF-8");
				map.put("sign_type", "RSA");
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
				rs.setRetMsg(resMap.get("ret_msg"));
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

	@RequestMapping(value = "/downloadSettleFile", method = RequestMethod.POST)
	@ResponseBody
	public String downloadSettleFile(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<>();
		try {
			map = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
			map.put("service", "download_settle_file");
			map.put("sign_type", "RSA");
			map.put("version", "4.0");

			try {
				// get sign
				ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
				Map<String, String> fieldMap = new HashMap<>();
				fieldMap = reqDataPost.getField();
				//fieldMap.put("goods_data", URLEncoder.encode(goodsData, "UTF-8").toString());//用于发送数据
				String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);

				HttpPost httpPost = new HttpPost(reqDataPost.getUrl());
				CloseableHttpClient httpclient = HttpClients.createDefault();
				List <NameValuePair> nvps = new ArrayList <NameValuePair>();
				for(Map.Entry<String, String> entry : fieldMap.entrySet()){
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse response = httpclient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

			 	SimpleDateFormat sdf = new  SimpleDateFormat("yyyyMMddHHmmss");
			 	String name = ((String) map.get("mer_id")).concat("_" + sdf.format(new Date())).concat(".txt");

				String filePath = "E:/work/downloadSettleFile/".concat(name);
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				int inByte;
				while((inByte = is.read()) != -1)
				     fos.write(inByte);
				is.close();
				fos.close();
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
			//rs.setUrl(reqDataPost.getUrl());
			rs.setRetMsg(resMap.get("ret_msg"));
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
			map.put("notify_url", "www.google.com");
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

	@RequestMapping(value="/getExchangeRate", method=RequestMethod.POST)
	@ResponseBody
	public String getExchangeRate(HttpServletRequest req, @RequestBody String reqBody) throws UnsupportedEncodingException{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("service", "cb_exchange_rate_query");
		map.put("sign_type", "RSA");
		map.put("charset", "UTF-8");
		map.put("mer_id", reqMap.get("merId"));
		map.put("version", "4.0");
		map.put("user_ip", "10.1.1.1");
		map.put("currency", reqMap.get("curName"));

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			try {
				reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
			} catch (Exception e) {
				throw new ReqDataException(e.getMessage());
			}
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		if("0000".equals(resMap.get("ret_code"))){
			rs.setSuccess(true);
			rs.setMsg("succeed");
		}else{
			rs.setRetMsg(resMap.get("ret_msg"));
		}
		Map<String, Object> rspMap = new HashMap<>();
		rs.setInfo(rspMap);
		rspMap.putAll(resMap);

		String jsonInString = "{}";
		try {
			jsonInString = mapper.writeValueAsString(rs);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	@RequestMapping(value = "/notifyResult", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String notifyResultListener(HttpServletRequest req){

		Map<String, String> map = new HashMap<>();
		StringBuilder retStr = new StringBuilder("<META NAME=\"MobilePayPlatform\" CONTENT=\"");
		StringBuilder payResultStr = new StringBuilder();
		Set<String> keySet = new HashSet<>();
		keySet.add("mer_id");
		keySet.add("sign_type");
		keySet.add("version");
		keySet.add("order_id");
		keySet.add("mer_date");
		try {
			map = Plat2Mer_v40.getPlatNotifyData(req);
			for(Map.Entry entry : map.entrySet()){
				payResultStr.append(entry.getKey());
				payResultStr.append("=");
				payResultStr.append(entry.getValue());
				payResultStr.append("\n");
				if(keySet.contains(entry.getKey())){
					retStr.append(entry.getKey());
					retStr.append("=");
					retStr.append(entry.getValue());
					retStr.append("&");
				}
			}
            String retMsg = sendMail(payResultStr.toString());
            if("".equals(retMsg)){
            	retStr.append("ret_code=0000&");
    			retStr.append("ret_msg= Test merchants result and notification response data &");
    			retStr.append("sign=");
    			retStr.append(map.get("sign"));
    			retStr.append("\" />");
            }else{
            	retStr = new StringBuilder(retMsg);
            }
		} catch (VerifyException e) {
			e.printStackTrace();
			retStr = new StringBuilder(e.getMessage());
		}
		return retStr.toString();
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
	
	private String sendMail(String str){
		final String username = "kevinli@umpay.com";
        final String password = "Woshilige123!";
        String retMsg = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "cus-umpay.bestcloudwan.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.trust", "cus-umpay.bestcloudwan.com");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kevinli@umpay.com"));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse("kevinli@umpay.com"));
            message.setSubject("A testing mail header !!!");
            message.setText(str);
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            // throw new RuntimeException(e);
        	retMsg = e.getMessage();
            System.out.println();
        }
        return retMsg;
	}
}
