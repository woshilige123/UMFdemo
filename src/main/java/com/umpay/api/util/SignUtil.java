
package com.umpay.api.util;

import java.security.Signature;
import java.security.cert.X509Certificate;
import com.umpay.api.common.Base64;
import com.umpay.api.log.ILogger;
import com.umpay.api.log.LogManager;

/**
 * ***********************************************************************
 * <br>description : 签名验签工具类
 * @author      umpay
 * @date        2014-8-1 上午09:39:24
 * @version     1.0  
 ************************************************************************
 */
public class SignUtil {

	private static ILogger log_ = LogManager.getLogger();
	public static final String KEY_ALGORITHM = "RSA";
	
	/**
	 * 
	 * <br>description : 商户请求平台数据签名
	 * @param plain
	 * @param merId
	 * @return
	 * @version     1.0
	 * @date        2014-8-1上午09:39:50
	 */
	public static String sign(String plain,String merId){
		log_.debug("plain="+plain);
		try{
			Signature sig = Signature.getInstance("SHA1withRSA");
			//Signature sig = Signature.getInstance("SHA256withRSA");
	        sig.initSign(PkCertFactory.getPk(merId));
	        sig.update(plain.getBytes("gbk"));
	        //sig.update(plain.getBytes("UTF-8"));
	        byte signData[] = sig.sign();
	        String sign = new String(Base64.encode(signData));
	        log_.debug("sign="+sign);
	        return sign;
		}catch(Exception ex){
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
	
	/**
	 * 
	 * <br>description : 商户验证平台数据签名
	 * @param sign
	 * @param plain
	 * @return
	 * @version     1.0
	 * @date        2014-8-1上午09:40:03
	 */
	public static boolean verify(String sign,String plain){
		log_.debug("sign="+sign);

		log_.debug("plain="+plain);
		X509Certificate cert = PkCertFactory.getCert();
		log_.info("Successfully got certafication.");
		try{
			byte[] signData = Base64.decode(sign.getBytes());
			 Signature sig = Signature.getInstance("SHA1withRSA");
	            sig.initVerify(cert);
	            sig.update(plain.getBytes("gbk"));
	            boolean b = sig.verify(signData);
	            log_.info("Result of verifying data from umpay platform :"+b);
	            return b;
		}catch(Exception ex){
			log_.info("Exception occurs when dencrypting data."+ ex);
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
}
