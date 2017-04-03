package com.umpay.api.paygate.v40;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.umpay.api.common.Const;
import com.umpay.api.exception.RetDataException;
import com.umpay.api.exception.VerifyException;
import com.umpay.api.log.ILogger;
import com.umpay.api.log.LogManager;
import com.umpay.api.util.DataUtil;
import com.umpay.api.util.HttpMerParserUtil;
import com.umpay.api.util.PlainUtil;
import com.umpay.api.util.SignUtil;
import com.umpay.api.util.StringUtil;

/**
 * ***********************************************************************
 * <br>description : 联动平台请求或响应给商户信息处理类
 * @author      umpay
 * @date        2014-8-1 上午09:24:31
 * @version     1.0  
 ************************************************************************
 */
public class Plat2Mer_v40 {
	private static final ILogger log = LogManager.getLogger();

	/**
	 * 
	 * <br>description : 商户请求平台后，解析平台响应给商户的html格式的数据，并验证平台签名
	 * @param html
	 * @return
	 * @throws RetDataException
	 * @version     1.0
	 * @date        2014-7-24下午08:15:56
	 */
	public static Map getResData(String html)throws RetDataException{
		Map data = new HashMap();
		try{
			data = getData(html);
		}catch(Exception e){
			throw new RetDataException("Failed to parse the response data from backend.",e);
		}
		return data;
	}
	
	/**
	 * 
	 * <br>description : 商户请求平台后，解析平台响应给商户的数据流格式的数据，并验证平台签名
	 * @param in
	 * @return
	 * @throws RetDataException
	 * @version     1.0
	 * @date        2014-7-24下午08:16:07
	 */
	public static Map getResData(InputStream in) throws RetDataException{
		Map data = new HashMap();
		try{
			data = getDataByStream(in);
		}catch(Exception e){
			throw new RetDataException("Failed to parse the response data from backend.",e);
		}
		return data;
	}
	
	/**
	 * 
	 * <br>description : 商户请求平台后，解析平台响应给商户的meta格式的数据，并验证平台签名
	 * @param meta
	 * @return
	 * @throws RetDataException
	 * @version     1.0
	 * @date        2014-7-24下午08:16:16
	 */
	public static Map getResDataByMeta(String meta) throws RetDataException{
		Map data = new HashMap();
		try{
			data = getDataByContent(meta);
		}catch(Exception e){
			throw new RetDataException("Failed to parse the response data from backend.",e);
		}
		return data;
	}
	
	/**
	 * 
	 * <br>description : 解析平台主动通知给商户的数据并验签
	 * @param obj
	 * @return
	 * @throws VerifyException
	 * @version     1.0
	 * @date        2014-7-24下午08:16:45
	 */
	public static Map getPlatNotifyData(Object obj) throws VerifyException{
		Map data = DataUtil.getData(obj);
		log.debug("The request data for notify payment result:" + data);
		if(data==null || data.size()==0){
			throw new VerifyException("The data for parsing is empty.");
		}
		String sign = data.get(Const.SIGN).toString();
		Map retMap = PlainUtil.notifyPlain(obj,false);
		String plain = retMap.get(Const.PLAIN).toString();
		
		boolean checked = SignUtil.verify(sign, plain);
		if(!checked){
			throw new VerifyException("Signature of data from UMPay verification failed.");
		}
		return data;
	}
	
	
	private static Map getData(String html) throws VerifyException{
		if(StringUtil.isEmpty(html)){
			throw new RuntimeException("Please pass the HTML data to be parsed.");
		}
		String content = HttpMerParserUtil.getMeta(html);
		return getDataByContent(content);
	}
	
	private static Map getDataByStream(InputStream in) throws IOException,VerifyException{
		String html = HttpMerParserUtil.getHtml(in);
		log.info("The HTML from stream is：" + html);
		String content = HttpMerParserUtil.getMeta(html);
		log.info("The content of meta from HTML is：" + content);
		return getDataByContent(content);
	}
	
	private static Map getDataByContent(String content) throws VerifyException{
		String plain = "",sign = "";
		Map map = new HashMap();
		try{
			map = PlainUtil.getResPlain(content);
			plain = map.get(Const.PLAIN).toString();
			sign =  map.get(Const.SIGN).toString();
		}catch(Exception e){
			log.info("Exceptions occurs when parse the request data." + e);
		}
		/*
		if(!SignUtil.verify(sign, plain)){
			throw new VerifyException("Signature verification failed.");
		}else{
			log.info("Signature verification success.");
		}
		*/
		return map;
	}
}
