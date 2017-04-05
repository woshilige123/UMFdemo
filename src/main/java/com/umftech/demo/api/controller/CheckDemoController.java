package com.umftech.demo.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class CheckDemoController {

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
	@RequestMapping(value="/getCardBin", method=RequestMethod.POST)
	@ResponseBody
	public String getCardBin(HttpServletRequest req, @RequestBody String reqBody) throws UnsupportedEncodingException{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		reqMap.put("service", "cb_query_bin_msg");
		reqMap.put("sign_type", "RSA");
		reqMap.put("charset", "UTF-8");
		reqMap.put("version", "4.0");

		Map<String, String> resMap = new HashMap<>();
		try {
			ReqData reqDataPost;
			try {
				reqDataPost = Mer2Plat_v40.makeReqDataByPost(reqMap);
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
}
