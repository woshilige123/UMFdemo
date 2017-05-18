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
import com.umftech.demo.TestObj;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class TestController {

	@RequestMapping(value = "/test1", method = {RequestMethod.POST})
	@ResponseBody
	public String getBankList(HttpServletRequest req, @RequestBody String reqBody){

		System.out.println(reqBody);
		
		Gson gson = new Gson();
		TestObj testObj = gson.fromJson(reqBody, TestObj.class);
		testObj.setTestParam("from server.");

		return testObj.toJson();
		
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

}
