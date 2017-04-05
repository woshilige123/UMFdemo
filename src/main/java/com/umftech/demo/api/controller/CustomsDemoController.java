package com.umftech.demo.api.controller;

import java.io.UnsupportedEncodingException;
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
public class CustomsDemoController {

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
}
