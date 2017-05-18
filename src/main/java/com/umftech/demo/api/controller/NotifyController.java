package com.umftech.demo.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.umpay.api.util.DataUtil;

@RestController
@RequestMapping(value = "/demo", produces = "text/plain;charset=UTF-8")
public class NotifyController{

	/**
	 * Receive payment result from UMF
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/notifyResult", method = {RequestMethod.POST, RequestMethod.GET})
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
			map = DataUtil.getData(req);
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
            String retMsg = writeNotifyRecord(payResultStr.toString());
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
	private String sendMail(final String str){
		
		Thread td = new Thread(new Runnable(){

			@Override
			public void run() {
				final String username = "kevinli@umpay.com";
		        final String password = "Woshilige123!";

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
		            e.printStackTrace();
		        }
			}
			
		});
		td.start();
		
        return "";
	}
	
	private String writeNotifyRecord(String content){
		// TODO Auto-generated method stub
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		try{
			String name = System.getProperty("user.home")+File.separator+"notify"+File.separator+ formatDate.format(new Date()) + "_notify.txt";
	    	File writename = new File(name);
	    	if(!writename.exists()){
	        	writename.createNewFile(); // 创建新文件  
	        }
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
	        
	        out.write(content +"\r\n"); // \r\n即为换行  
	        out.flush(); // 把缓存区内容压入文件  
	        out.close(); // 最后记得关闭文件 
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
		return "";
	}
}
