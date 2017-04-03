package com.umpay.api.log;

/**
 * ***********************************************************************
 * <br>description : 日志管理器
 * @author      umpay
 * @date        2014-8-1 上午09:23:10
 * @version     1.0  
 ************************************************************************
 */
public class LogManager {

	private static ILogger logger ;
	
	/**
	 * 获取日志处理器
	 * @return
	 */
	public static ILogger getLogger(){
		if( null != logger)return logger;
		try{
			Class.forName("org.apache.log4j.Logger");
			logger = new Log4jLogger();
			logger.info("Print log using Log4j");
		}catch(ClassNotFoundException ex){
			logger = new SysOutLogger();
			logger.info("Print log using System.out");
		}
		return logger;
	}
	
	/**
	 * 注入logger
	 * @param logger_
	 */
	public static void setLogger(ILogger logger_){
		logger = logger_;
	}
}
