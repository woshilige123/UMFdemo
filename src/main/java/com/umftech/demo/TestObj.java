package com.umftech.demo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TestObj {
	private String param1;
	private String param2;
	private String param3;
	@SerializedName("test_param")
	private String testParam;
	public String getTestParam() {
		return testParam;
	}
	public void setTestParam(String testParam) {
		this.testParam = testParam;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
