package com.umftech.demo.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.umpay.api.common.Base64;
import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.util.PkCertFactory;
import com.umpay.api.util.SignUtil;
import com.umpay.api.util.StringUtil;
 
public class Test {

	public static void main(String[] args) throws ReqDataException, UnsupportedEncodingException {
		//String plain = "ret_code=00060700&ret_msg=请求参数fileNameRFXS_3602_20170209_USD_5910.txt中的信息与商户号、币种、批次号、批次日期不匹配";
		//String sign = SignUtil.sign(plain, StringUtil.trim("8023"));
		String test = "李";
		byte[] byteArr = test.getBytes("utf-16");
		for(int i = 0; i < byteArr.length; i++){
			if(i!=0 && i%4==0){
				System.out.print(" ");
			}
			System.out.print(byteArr[i]);
		}
	}
	
}