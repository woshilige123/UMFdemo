package com.umftech.demo.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.umftech.demo.TestObj;
import com.umpay.api.util.SignUtil;
import com.umpay.api.util.StringUtil;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class TestController {

	@RequestMapping(value = "/test1", method = {RequestMethod.POST})
	@ResponseBody
	public String getBankList(HttpServletRequest req, @RequestBody String reqBody){

		System.out.println(reqBody);
		
		//Gson gson = new Gson();
		//TestObj testObj = gson.fromJson(reqBody, TestObj.class);
		//testObj.setTestParam("from server.");

		//return testObj.toJson();
		return reqBody;
	}

	@RequestMapping(value = "/test2", method = {RequestMethod.GET})
	@ResponseBody
	public String getReferer(HttpServletRequest req){

		
		Gson gson = new Gson();
		String refererUrl = req.getHeader("Referer");
		Map<String, String> map = new HashMap<>();
		map.put("referer", refererUrl+"");
		map.put("sourceIP", req.getRemoteAddr());
		
		return gson.toJson(map);
		
	}
	@RequestMapping(value = "/generateSign", method = {RequestMethod.POST})
	@ResponseBody
	public String generateSign(HttpServletRequest req, @RequestBody String reqBody){

		
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(reqBody);
		JsonObject jOb = new JsonObject();
		String plain = jelement.getAsJsonObject().get("plain").getAsString();
		String merID = jelement.getAsJsonObject().get("merID").getAsString();
		//Map<String, String> reqMap = gson.fromJson(gson.toJsonTree(reqBody), new HashMap<String, String>().getClass());
		String sign = SignUtil.sign(plain, merID);
		jOb.addProperty("sign", sign);
		return jOb.toString();
		
	}

}
