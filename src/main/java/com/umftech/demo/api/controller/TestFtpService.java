package com.umftech.demo.api.controller;

import org.junit.Test;

import java.io.File;

/** 
 * desc: 测试ftp service
 * <p>创建人：曾祥江 创建日期：2016-3-29 </p>
 * @version V1.0  
 */
public class TestFtpService {
	/**
	 * desc: 测试ftp上传文件
	 * <p>创建人： 曾祥江 , 2016-3-29 下午8:51:03</p>
	 */
	@Test
	public void testFtpUpload(){
		FTPService ftpService = new FTPService();
		String localPath = "D:/";
		String fileName="RFXS_50575_20160711_USD_160711R5.txt";
		File file1 = new File(localPath, fileName);
        String ftpPath = "docs";
        File file2 = ftpService.upload(file1, ftpPath);
        if (file2.isFile()) {
            if (file2.exists()) { // 判断文件是否存在
                System.out.println("======文件上传成功");
            } else {
                System.out.println("======文件上传失败");
            }
        }else {
            System.out.println("======文件上传失败");
        }
	}

	/**
	 * desc: 测试ftp下载文件
	 * <p>创建人： 曾祥江 , 2016-3-29 下午10:10:49</p>
	 */
	//@Test
	public void testFtpDownload(){
        String ftpPath = "/home/docs";
		String fileName="RFXS_50575_20160711_USD_160711R5.txt";
		FTPService ftpService = new FTPService();
        File file = ftpService.download(ftpPath, fileName);
		if (null != file && file.isFile() && file.exists()) { // 判断文件是否存在
			System.out.println("======文件下载成功");
		}else {
			System.out.println("======文件下载失败");
		}
	}

}
