package com.umftech.demo.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.umftech.demo.TestObj;

@Controller
@RequestMapping(value = "/demo")
public class TestJSPController {

	
	@RequestMapping(value = "/testReferer", method = RequestMethod.GET)
	public String getRefererUrl(Model model, HttpServletRequest req){
		String refererUrl = req.getHeader("Referer");
		model.addAttribute("referer", refererUrl+"");
		model.addAttribute("sourceIP", req.getRemoteAddr());

		return "testReferer";
	}

}
