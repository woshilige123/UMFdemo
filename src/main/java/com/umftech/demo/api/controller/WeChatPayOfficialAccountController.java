package com.umftech.demo.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umftech.RestReturnTemp;
import com.umpay.api.common.ReqData;
import com.umpay.api.exception.VerifyException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;

@RestController
@RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
public class WeChatPayOfficialAccountController {
	
	/**
	 * Call the WeChat Official Pay service to complete payment.
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/officialAccoutPay", method = {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String payByWeChatOfficialAccount(HttpServletRequest req, @RequestBody String reqBody, HttpServletResponse res){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			reqMap = mapper.readValue(reqBody, new TypeReference<Map<String, String>>(){});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// orderId
		String orderId=""+(Math.round(Math.random()*800000000)+100000)+"";
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    String date = format.format(new Date());
	    orderId = date + orderId;
	    
	    // parameters
		reqMap.put("service", "publicnumber_and_verticalcode");
		reqMap.put("charset", "UTF-8");
		reqMap.put("sign_type", "RSA");
		reqMap.put("res_format", "HTML");
		reqMap.put("version", "4.0");
		reqMap.put("order_id", orderId);
		reqMap.put("mer_date", date); 
		reqMap.put("amt_type", "RMB");
		String getUrl = "";
		try {
			// use developer kit to get URL
			ReqData reqData = Mer2Plat_v40.makeReqDataByGet(reqMap);
			getUrl = reqData.getUrl();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		RestReturnTemp rs = new RestReturnTemp();
		rs.setSuccess(true);
		rs.setUrl(getUrl);

		String jsonInString = "{}";
		try {
			jsonInString = mapper.writeValueAsString(rs);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	/**
	 * Receive payment result from UMF
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/notifyWeChatPayResult", method = {RequestMethod.POST, RequestMethod.GET})
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
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
	    String time = format.format(new Date());
		try {
			map = Plat2Mer_v40.getPlatNotifyData(req);
			for(Map.Entry<String, String> entry : map.entrySet()){
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
			
            //String retMsg = sendMail(payResultStr.toString());
			try{
	        	String name = "/home/test/uattest/" + map.get("mer_priv") + "_result.txt";
	        	File writename = new File(name);
	        	
	        	if(!writename.exists()){
	            	writename.createNewFile(); // 创建新文件  
	            }
	            BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
	            StringBuilder fileContent = new StringBuilder();
	            fileContent.append("order_id");
	            fileContent.append("=");
	            fileContent.append(map.get("order_id"));
	            fileContent.append("&");
	            fileContent.append("trade_state");
	            fileContent.append("=");
	            fileContent.append(map.get("trade_state"));
	            fileContent.append("\n");
	            out.write(fileContent.toString()); // \r\n即为换行  
	            out.flush(); // 把缓存区内容压入文件  
	            out.close(); // 最后记得关闭文件 
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }
			String retMsg = "";
            if("".equals(retMsg)){
            	retStr.append("ret_code=0000&");
    			retStr.append("ret_msg= Test merchants result and notification response data &");
    			retStr.append("sign=");
    			retStr.append(map.get("sign"));
    			retStr.append("\" />");
            }else{
            	retStr = new StringBuilder(retMsg);
            }
		} catch (Exception e) {
			e.printStackTrace();
			retStr.append(e.getMessage());
			retStr.append("\n");
			retStr.append(time);
			sendMail(retStr.toString());
		}
		return retStr.toString();
	}
	
	/**
	 * Send mail
	 * 
	 * @return String
	 */
	private String sendMail(String str){
		//TODO
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
