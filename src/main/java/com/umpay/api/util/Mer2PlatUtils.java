package com.umpay.api.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.umpay.api.common.Const;
import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ParameterCheckException;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.log.ILogger;
import com.umpay.api.log.LogManager;

/**
 * ***********************************************************************
 * <br>description : 商户请求联动平台组织数据帮助类
 * @author      umpay
 * @date        2014-7-25 上午10:53:38
 * @version     1.0  
 ************************************************************************
 */
public class Mer2PlatUtils {
	private static ILogger log_ = LogManager.getLogger();
	
	/**
	 * 
	 * <br>description :V4.0版获取请求对象,组织时校验请求数据格式
	 * @param appname 请求联动接入系统应用名称
	 * @param obj 请求数据对象
	 * @param method 请求方式 get or post
	 * @return
	 * @throws ReqDataException
	 * @version     1.0
	 * @date        2014-7-25上午10:57:54
	 */
	public static ReqData getReqData(String appname,Object obj ,String method) throws ReqDataException{
		if(obj==null || "".equals(method) ){
			log_.info("Parameter is incorrect : Obj or method is null.");
			throw new RuntimeException("Parameter is incorrect : Obj or method is null.");
		}
		
		//得到数据对象
		Map<String, String> map = DataUtil.getData(obj);
		Map<String, String> mapfield = new HashMap();;
		if(map.get(Const.SERVICE)==null || !ServiceMapUtil.getServiceRule().containsKey(map.get(Const.SERVICE)))
			throw new ParameterCheckException("The service type is incorrect : service is null or the field is incorrect.");
		
		doEncrypt(map);
		
		//组织请求联动的url 
		ReqData data = new ReqData();
		String url = getUrl(appname,map.get("is_wechat_accout"));
		log_.debug("url=" + url);
		
		//校验请求数据格式、生成签名
		Map returnMap = PlainUtil.getPlain(obj);
		
		//组织http请求数据
		String plain = returnMap.get(Const.PLAIN).toString();
		String sign = returnMap.get(Const.SIGN).toString();
		if(Const.METHOD_GET.equalsIgnoreCase(method)){
			try{
				sign = URLEncoder.encode(sign,"UTF-8");
			}catch(Exception e){
				throw new ReqDataException("Exception occurs when encoding the signature. Please contact us(UMPay).");
			}
			String param = plain + "&" + "sign=" + sign;
			log_.debug("param=" + param);
			data.setUrl(url + "?" + param);
			log_.info("The complete url to access UMPay is：" + data.getUrl());
			data.setPlain(plain);
			data.setSign(sign);
			log_.info("Data for returning to merchant："+data.toString());
			return data;
		}else if(Const.METHOD_POST.equalsIgnoreCase(method)){
			data.setUrl(url);
			map.put(Const.SIGN, sign);
			for(String key : map.keySet()){
				if(StringUtil.isNotEmpty(StringUtil.trim(map.get(key)))){
					mapfield.put(key, map.get(key));
				}
			}
			data.setField(mapfield);
			data.setSign(sign);
			data.setPlain(plain);
			log_.info("Data for returning to merchant:"+data.toString());
			return data;
		}else{
			throw new RuntimeException("The method that could get from request:" + method);
		}
	}
	
	/**
	 * V4.0版获取请求对象，组织时不校验请求数据格式
	 * @param obj 请求数据对象
	 * @param method 请求方式 get or post
	 * @return
	 * @throws ReqDataException
	 */
	public static ReqData makeReqData(String appname,Object obj ,String method) throws ReqDataException{
		//得到数据对象
		Map<String, String> map = DataUtil.getData(obj);
		Map<String, String> mapfield = new HashMap();
		//需RSA加密字段
		doEncrypt(map);
		
		//组织请求联动的url
		String url = getUrl(appname,map.get("is_wechat_accout"));
		
		log_.debug("url=" + url);
		if(obj==null ||StringUtil.isEmpty(method) ){
			log_.info("Parameter is incorrect : Obj or method is null.");
			throw new RuntimeException("Parameter is incorrect : Obj or method is null.");
		}
		
		//获取签名
		Map returnMap = PlainUtil.getPlainNocheck(obj);
		String plain = returnMap.get(Const.PLAIN).toString();
		String sign = returnMap.get(Const.SIGN).toString();
		
		//组织http请求数据
		ReqData data = new ReqData();
		if(Const.METHOD_GET.equalsIgnoreCase(method)){
			try{
				sign = URLEncoder.encode(sign,"UTF-8");
			}catch(Exception e){
				throw new ReqDataException("Exception occurs when encoding the signature. Please contact us(UMPay).");
			}
			String param = plain + "&" + "sign=" + sign;
			log_.debug("param=" + param);
			data.setUrl(url + "?" + param);
			log_.info("The complete url to access UMPay is：" + data.getUrl());
			data.setPlain(plain);
			data.setSign(sign);
			return data;
		}else if(Const.METHOD_POST.equalsIgnoreCase(method)){
			data.setUrl(url);
			map.put(Const.SIGN, sign);
			for(String key : map.keySet()){
				if(StringUtil.isNotEmpty(StringUtil.trim(map.get(key)))){
					mapfield.put(key, map.get(key));
				}
			}
			data.setField(mapfield);
			data.setSign(sign);
			data.setPlain(plain);
			log_.info("Data for returning to merchant:"+data.toString());
			return data;
		}else{
			throw new RuntimeException("The method that could get from request:" + method);
		}
	}
	private static void doEncrypt(Map map) throws ReqDataException {
		//从配置文件获取需要加密的要素
		String encryptParamters = StringUtil.trim(ProFileUtil.getPro("Encrypt.Paramters"));
		String[] params = encryptParamters.split(",");
		HashSet encryptId = new HashSet();
		for(String param:params){
			if(StringUtil.isNotEmpty(param)){
				encryptId.add(param);
			}
		}
		//如果没有获取到，去默认的
		if(encryptId.size()==0){
			encryptId = ServiceMapUtil.getEncryptId();
		}
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			Object ob = map.get(key);
			String value = null;
			if(ob!= null){
				value = ob.toString();
			}
			try{
				if(encryptId.contains(key)&& StringUtil.isNotEmpty(value)){
					value = CipherUtil.Encrypt(value);
					map.put(key,value);
				}
			}catch(Exception e){
				log_.info("request data:"+ key+ "=" + value +"Exception occurs when encrypting data." + e.getMessage());
				throw new ReqDataException("Exception occurs when encrypting data by using public key.");
			}
		}
	}
	
	private static String getUrl(String appname,String isWeChat){
		//获取配置的平台URL
		String platurl =  ProFileUtil.getUrlPix();
		String url = "";
		if(platurl==null||"".equals(platurl.trim())){
			platurl = "http://pay.soopay.net";
		}
		if("Y".equals(isWeChat)){
			url = platurl +"/" + appname+ "/pay/cbPreAuthDirect.do";
		}else{
			url = platurl +"/" + appname+ Const.UMPAYSTIE_SERVICE;
		}
		return url;
	}
}
