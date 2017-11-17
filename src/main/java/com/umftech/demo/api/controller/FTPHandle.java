package com.umftech.demo.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/** 
 * desc: 从ftp服务器上下载文件
 * <p>创建人：曾祥江 创建日期：2016-3-29 </p>
 * @version V1.0  
 */
public class FTPHandle {
    private Logger log = Logger.getLogger(FTPHandle.class);
	private FTPClient ftpClient;

	private String ip = "";
	private int port = 21;
	private String username = "";
	private String password = "";

	public FTPHandle(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
        log.info(String.format("ftp handle ip: %s, port: %s, username: %s, password: %s",ip,port,username,password));
    }
	
	/**
	 * desc: 连接到服务器
	 * <p>创建人：曾祥江 , 2016-3-29 下午3:53:29</p>
	 */
	public boolean connectServer() {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
				ftpClient = new FTPClient();
				ftpClient.setDefaultPort(port);
				ftpClient.configure(getFtpConfig());
				ftpClient.connect(ip);
				ftpClient.login(username, password);
                ftpClient.setBufferSize(1024);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                log.info(String.format("ftpClient.getReplyString(): %s", ftpClient.getReplyString()));
                reply = ftpClient.getReplyCode();
//				ftpClient.setDataTimeout(120000);
				ftpClient.enterLocalPassiveMode();// 设置ftp访问模式，防止防火墙问题导致流程挂起
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					log.error("FTP server refused connection.");
					return false;
				}
			}catch (SocketException e) {
				flag=false;
				e.printStackTrace();
				log.error(String.format("登录ftp服务器 %s 失败,连接超时！", ip));
                throw new IllegalStateException(String.format("登录ftp服务器 %s 失败,连接超时！", ip));
			}catch (IOException e) {
				flag=false;
				e.printStackTrace();
				log.error(String.format("登录ftp服务器 %s 失败，FTP服务器无法打开！", ip));
                throw new IllegalStateException(String.format("登录ftp服务器 %s 失败,连接超时！", ip));
			}
		}
		return flag;
	}

	// 从ftp下载文件
	public File download(String ftpPath, String localPath, String fileName) throws IOException {
		File localFile;
//		Date d = new Date();
//		String separator = System.getProperty("file.separator");// 分隔符
		localFile = new File(localPath, fileName);//new File(localPath + separator + "temp" + d.getTime());
        createPath(localFile);
        log.info(String.format("下载到localFile: %s",localFile));
		// 转移FTP服务器目录至指定的目录下
        boolean isTrue = ftpClient.changeWorkingDirectory(ftpPath);
        if (!isTrue){
            log.info("ftp切换目录失败");
            throw new IllegalStateException("ftp切换目录失败");
        }
		// 获取文件列表
		String[] fileFtp = ftpClient.listNames();
        log.info("fileFtp.length: " + fileFtp.length);
        if (0 != fileFtp.length) {
            for (String ff : fileFtp) {
                if (ff.equals(fileName)) {
                    OutputStream os = new FileOutputStream(localFile);
                    if (!ftpClient.retrieveFile(fileName, os)) {
                        log.info("Could not download the file. " + fileName);
                        os.close();
                        return null;
                    } else {
                        log.info("Downloaded file @ : " + fileName);
                        os.close();
                        return localFile;//new File(localPath + separator + fileName);
                    }
                }
            }
        }
//		localFile.renameTo(new File(localPath + separator + fileName));
		return null;
	}

    /**
     * 如果要下载的文件已存在，则删除文件，不存在则判断是否要创建文件目录
     * @param file 要创建目录的文件
     */
    private void createPath(File file) {
        if (file.exists()){
            if (file.delete()){
                log.info(String.format("删除旧文件%s成功",file.getName()));
            }else {
                log.info(String.format("删除旧文件%s失败",file.getName()));
            }
        }else {
            if (file.getParentFile().exists()){
                log.info("文件路径存在，开始下载文件");
            }else {
                if(file.getParentFile().mkdirs()) {
                    log.info(String.format("创建目录%s成功", file.getParentFile()));
                }else {
                    log.info(String.format("创建目录%s失败", file.getParentFile()));
                }
            }
        }
    }

    // ftp上传文件
	public void upload(File file, String ftpPath) throws Exception {
		File file2 = new File(file.getPath());
		FileInputStream input = new FileInputStream(file2);
		ftpClient.changeWorkingDirectory(ftpPath);
		ftpClient.storeFile(file2.getName(), input);
		input.close();
	}

	/**
	 * 关闭连接
	 */
	public void closeConnect() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				log.info("关闭ftp链接");
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * desc: 设置FTP客服端的配置--一般可以不设置
	 * <p>创建人：曾祥江 , 2016-3-29 下午3:51:40</p>
	 * @return
	 */
	private FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}

}
