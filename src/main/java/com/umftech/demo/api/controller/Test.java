package com.umftech.demo.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.umpay.api.common.Base64;
import com.umpay.api.util.PkCertFactory;
import com.umpay.api.util.SignUtil;
import com.umpay.api.util.StringUtil;
 
public class Test {

	public static void main(String[] args) {
		//String plain = "ret_code=00060700&ret_msg=请求参数fileNameRFXS_3602_20170209_USD_5910.txt中的信息与商户号、币种、批次号、批次日期不匹配";
		//String sign = SignUtil.sign(plain, StringUtil.trim("8023"));
		
		try{
        	File writename = new File("E:\\work\\doc\\doc\\01-MerInterface\\result.txt");
            if(!writename.exists()){
            	writename.createNewFile(); // 创建新文件  
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));  
            out.write("test" +"\r\n"); // \r\n即为换行  
            out.flush(); // 把缓存区内容压入文件  
            out.close(); // 最后记得关闭文件 
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		
		
		System.out.println(Math.random()*10);
		System.out.println(Math.round(Math.random()*800000000)+100000);
	}
	public static void testSign(){
		String sign = "TDqgqFBufH/rSkOt3OtjFJFIAWcPkc8dSW/bGnpALZAkVJTUQYaw3OK7leGPceU6Ku82sa9gJf3t2PZO/ztxZiyRibcLRF3QTu0w3TPJXNjCF9rbDcdsiU0lMU8NzHoggpCXrIVFVJASe4vf4WVsaq4OJnqClsRjVifwI87ZsoQ=";
		String str = "mer_id=9844&ret_code=00060711&ret_msg=商户未开通&version=4.0";
		X509Certificate cert = PkCertFactory.getCert();
		System.out.println("获取证书成功!");
		try{
			byte[] signData = Base64.decode(sign.getBytes());
			 Signature sig = Signature.getInstance("SHA1withRSA");
	            sig.initVerify(cert);
	            sig.update(str.getBytes("gbk"));
	            boolean b = sig.verify(signData);
	            System.out.println("验证平台签名是否成功:"+b);
		}catch(Exception ex){
			System.out.println("解密异常"+ ex);
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
	public static String testSign2(String str){
		String sign = SignUtil.sign(str,StringUtil.trim("8023"));
		return sign;
	}
	
}