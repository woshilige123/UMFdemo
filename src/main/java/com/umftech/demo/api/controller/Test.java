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

	public static void main(String[] args) throws ReqDataException {
		//String plain = "ret_code=00060700&ret_msg=请求参数fileNameRFXS_3602_20170209_USD_5910.txt中的信息与商户号、币种、批次号、批次日期不匹配";
		//String sign = SignUtil.sign(plain, StringUtil.trim("8023"));
		Map<String, String> map = new HashMap<>();
        map.put("amount", "0.01");
        map.put("card_holder", "罗淳雅");
        map.put("charset", "UTF-8");
        map.put("currency", "CNY");
        map.put("goods_data", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><goods_data><sub_order><sub_order_id>0504675560349</sub_order_id><sub_order_amt>0.01</sub_order_amt><sub_trans_code>02223022</sub_trans_code></sub_order></goods_data>");
        map.put("goods_inf", "test");
        map.put("identity_code", "431381198109106573");
        map.put("identity_type", "IDENTITY_CARD");
        map.put("mer_date", "20170508");
        map.put("mer_id", "3965");
        map.put("mobile_id", "15011466525");
        map.put("order_id", "20170504675560349");
        map.put("pay_type", "WECHAT");
        map.put("res_format", "STRING");
        map.put("risk_expand", "A0001:123659973");
        map.put("service", "cb_active_scancode_pay");
        map.put("sign_type", "RSA");
        map.put("user_ip", "10.10.10.10");
        map.put("version", "4.0");
        map.put("notify_url", "www.google.com");
        
        ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(map);
        String plain = "amount=0.01&card_holder=aKPfUqWAXG10Lpd4nOo96ObhBQ1%2fAYc9tF5cpjaWuWvE8nh%2fpUtKWafVegkdatP0AJhrmLvs9KDu7dp39FkL0eqXvCrzEYmbcMVkhe3EEAzGOVk871K68Cn7mpIW14rmUmRwmY5LRDDV%2b28M46xpAeDC%2bnRP38Wzb8EPhPAjlc4%3d&charset=UTF-8&currency=CNY&goods_data=%3c%3fxml+version%3d%221.0%22+encoding%3d%22UTF-8%22%3f%3e%3cgoods_data%3e%3csub_order%3e%3csub_order_id%3e0504675560349%3c%2fsub_order_id%3e%3csub_order_amt%3e0.01%3c%2fsub_order_amt%3e%3csub_trans_code%3e02223022%3c%2fsub_trans_code%3e%3c%2fsub_order%3e%3c%2fgoods_data%3e&goods_inf=test&identity_code=NzgQy6G%2bFr4z1gmfezwbBy1EI9QEBuHCevFH08i7HhzmV3KMCXZQgO5VThkJUHw86p5XRQcsRHqtGswuCGPHqYBAaj7w3rOdx%2fgmvODk9maOxDi4Ff%2fR4jFoDMEVrEFaNc4NLDK%2fjIMiwBXQVLza06zngKH3CveSBsato4Y7e08%3d&identity_type=IDENTITY_CARD&mer_date=20170508&mer_id=8023&mobile_id=15011466525&notify_url=www.google.com&order_id=20170504675560349&pay_type=WECHAT&res_format=STRING&risk_expand=A0001%3a123659973&service=cb_active_scancode_pay&sign_type=RSA&user_ip=10.10.10.10&version=4.0";
        String sign = "QvEmgCs//BkaeI9z4SyjpKKIQgPPtrUMn1AfXmjHvnJ+ygkuLyTJUOLdPTLt9q8GceaRluJX/0Hos51oX10D8HzW/mm1L3sUMVS2w5BiQvYyqrXdHcOfW8ho9xGn97+Qfz9bhOIM6sfl5inLf/Zc9NQWCCnXPZCypFLMF2HhAgw=";
        boolean vali = SignUtil.verify(reqDataPost.getSign(), reqDataPost.getPlain());
        System.out.println(vali);
        System.out.println(reqDataPost.getPlain());
        System.out.println(reqDataPost.getField());

	}
	
}