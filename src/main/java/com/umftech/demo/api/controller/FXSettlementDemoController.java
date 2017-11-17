package com.umftech.demo.api.controller;

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
@RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
public class FXSettlementDemoController {
	

	@RequestMapping(value = "/uploadReceiptFile", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String noticeFileUpload(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "receipt_file_upload");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/registerEnterprise", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String registerEnterprise(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "register_enterprise");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		//reqMap.put("req_date", date);
		//reqMap.put("req_time", time);

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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


	@RequestMapping(value = "/checkRegEnterprise", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkRegEnterprise(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "query_enterprise");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("req_date", date);
		reqMap.put("req_time", time);

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/payeeReg", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String registePayee(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "add_payment_info");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("req_date", date);
		reqMap.put("req_time", time);
		reqMap.put("in_account_pro", "FOR_PRIVATE");
		

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/payeeCheck", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String queryPayeeInfo(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "query_payment_info");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("req_date", date);
		reqMap.put("req_time", time);
		

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/payeeState", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String changePayeeState(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "operation_payment_state");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("req_date", date);
		reqMap.put("req_time", time);
		
		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/confirmPayment", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String confirmPayment(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "confirm_remittance");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/checkExchangeStatus", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkExchangeStatus(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "receipt_query");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/checkSettlementStatus", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkSettlementStatus(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "remittance_query");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/checkFileParseResult", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkFileParseResult(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
	    String date = formatDate.format(new Date());
	    String time = formatTime.format(new Date());
		reqMap.put("service", "file_batch_query");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("req_date", date);
		reqMap.put("req_time", time);

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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

	@RequestMapping(value = "/downloadSettleFile", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String downloadSettleFile(HttpServletRequest req, @RequestBody String reqBody){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		reqMap.put("service", "remittance_query");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
			Map<String, String> fieldMap = new HashMap<>();
			fieldMap = reqDataPost.getField();
			//String resultString = HttpClientUtil.doPost(reqDataPost.getUrl(), fieldMap);
			String resultString = HttpClientUtil.doPost("http://fxsettlement.soopay.net/cbeweb/cbe/cbeservice.do", fieldMap);
			resMap = Plat2Mer_v40.getResData(resultString);

		} catch (Exception e1) {
			e1.printStackTrace();
			resMap.put("ret_msg", e1.getMessage());
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
}
