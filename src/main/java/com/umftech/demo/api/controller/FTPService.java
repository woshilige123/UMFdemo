package com.umftech.demo.api.controller;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;

/** 
 * desc: 
 * <p>创建人：曾祥江 创建日期：2016年3月29日 </p>
 * @version V1.0  
 */
public class FTPService {
	private Logger log = Logger.getLogger(FTPService.class);

	/**
	 * desc: ftp获取配置文件中ip等信息，下载文件
	 * <p>创建人： 曾祥江 , 2016年3月29日 下午5:47:25</p>
	 * @param ftpPath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @return 返回下载的文件
	 */
	public File download(String ftpPath, String fileName){
		String[] params = fileName.split("_");
		String merId = params[1].trim();
		log.info(String.format("ftp download %s 商户的文件",merId));

		// String ip = SpliceUtil.getMerParams(merId, "receipt.upload.ftpServerIP");//ftp服务器的IP
		String ip = "47.88.61.8";//ftp服务器的IP
        String userName = "ftptest";//ftp服务器的用户名
        String pwd = "mpsp";//ftp服务器的密码
        log.info(String.format("[收汇]获取文件 ftpPath: %s, fileName: %s", ftpPath,fileName));

		FTPHandle ftpHandle = new FTPHandle(ip, 22, userName, pwd);
		//从ftp服务器下载文件
		File file;
		String localPath = "E:/";
		try {
			if (ftpHandle.connectServer()) {
                log.info(String.format("ftp下载 ip: %s, userName: %s, password: %s, 路径: %s",ip,userName,pwd,ftpPath));
				file = ftpHandle.download(ftpPath, localPath, fileName);
				return file;
			}else
				return null;
		} catch (IOException e) {
			log.error(String.format("文件：%s 下载失败！",fileName), e);
			return null;
		} finally {
			ftpHandle.closeConnect();// 关闭ftp链接
		}
	}

	/**
	 * desc: 文件上传
	 * <p>创建人： 曾祥江 , 2016年4月13日 下午12:55:50</p>
	 * @param file 要上传的文件
	 * @param ftpPath FTP服务器上的相对路径
	 * @return
	 */
	public File upload(File file, String ftpPath){
		// ********************上传ftp服务器*********************//
        String fileName = file.getName();
		String[] params = fileName.split("_");
		String merId = params[1].trim();
		log.info(String.format("ftp upload merId: %s",merId));

		String ip = "47.88.61.8";//ftp服务器的IP
        String userName = "ftptest";//ftp服务器的用户名
        String pwd = "mpsp";//ftp服务器的密码
        log.info(String.format("[收汇]上传文件 serverIp: %s, userName: %s, pwd: %s", ip,userName,pwd));
        log.info(String.format("[收汇]上传文件 ftpPath: %s, fileName: %s", ftpPath,fileName));

        

        
        
		FTPHandle ftpHandle = new FTPHandle(ip, 22, userName, pwd);
		try {
			//String knownHostPublicKey = "47.88.61.8 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBB62YxGHitRr5d/+9sAq+mVC4ag/DORV/AOyGjqkynmsjcTfSoLSz645kFtR+kEWU6rcbQ3//WkLcBKWPG/LARE=";

			
	        JSch jsch = new JSch();
	        //jsch.setKnownHosts(System.getProperty("user.home") + File.separator + ".ssh" + File.separator + "known_hosts");

	        //jsch.getHostKeyRepository().add("");
	        
	        String keyString = "AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBB62YxGHitRr5d/+9sAq+mVC4ag/DORV/AOyGjqkynmsjcTfSoLSz645kFtR+kEWU6rcbQ3//WkLcBKWPG/LARE=";
	        jsch.addIdentity(System.getProperty("user.home") + File.separator + ".ssh" + File.separator + "id_rsa");
			 // parse the key
			 // byte [] key = Base64.getDecoder().decode ( keyString ); // Java 8 Base64 - or any other
			
			 // HostKey hostKey = new HostKey (InetAddress.getLocalHost().getHostName(), key);
			
			 // add the host key
			 // jsch.getHostKeyRepository ().add ( hostKey, null );
	     
	     
	        Session session = jsch.getSession( userName, ip, 22);
	        session.setConfig( "PreferredAuthentications", "publickey,keyboard-interactive,password" );
	        session.setPassword( pwd );
	        //session.setConfig("StrictHostKeyChecking", "no");
	        session.connect( 1000 );
	        Channel channel = session.openChannel( "sftp" );
	        ChannelSftp sftp = ( ChannelSftp ) channel;
	        sftp.connect( 1000 );
	        
	        
	        //ChannelSftp sftp = connectWithKey(ip, userName, pwd, 22);
	        
	        
	        
			if(ftpHandle.connectServer()) {
				log.info(String.format("ftp上传 ip: %s, userName: %s, password: %s, 路径: %s", ip, userName, pwd, ftpPath));
				ftpHandle.upload(file, ftpPath);
				log.info("**************FTP文件上传成功***********");
			}else {
                log.info("**************FTP登录失败*********");
                return null;
			}
		} catch (Exception e) {
			log.error(e.toString(),e);
			throw new RuntimeException("The FTP server connection failed!", e);
		} finally {
			ftpHandle.closeConnect();
			log.info("The FTP server normally closed!");
		}
		return file;
	}
	
	public ChannelSftp connectWithKey(String ip, String username, String password, int port) throws Exception{  
	    JSch jsch = new JSch();  

	    jsch.addIdentity("C:\\Users\\ligua\\.ssh\\id_rsa");
	    Session session = null;  
	    if(port <=0){//连接服务器，采用默认端口  
	        session = jsch.getSession(username, ip);  
	    }else{//采用指定的端口连接服务器  
	        session = jsch.getSession(username, ip ,port);  
	    }  
	    if (session == null) {//如果服务器连接不上，则抛出异常  
	        throw new Exception("session is null");  
	    }  
	    if(!StringUtils.isEmpty(password))//密码不为空则设置密码  
	        session.setPassword(password);  
	  
	    session.connect();//设置登陆超时时间  
	    Channel channel = (Channel)session.openChannel("sftp");//创建sftp通信通道  
	    channel.connect();  
	    ChannelSftp sftp = (ChannelSftp)channel;  
	    return sftp;  
	}  

}
