package com.umftech;

import java.util.Map;

public class RestReturnTemp {
	private boolean success;
	private String msg;
	private String sign;
	private String tradeNo;
	private String tradeState;
	private String orderId;
	private String merDate;
	private String retMsg;
	private String url;
	private String payUrl;
	private String bankList;
	private String refundStatus;
	private String customStatus;
	private String currency;
	private String refundAmt;
	private String refundCnyAmt;
	private String appId;
	private String timeStamp;
	private String packageJson;
	private String nonceStr;
	private String paySign;

	public String getMerDate() {
		return merDate;
	}
	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getPackageJson() {
		return packageJson;
	}
	public void setPackageJson(String packageJson) {
		this.packageJson = packageJson;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getCustomStatus() {
		return customStatus;
	}
	public void setCustomStatus(String customStatus) {
		this.customStatus = customStatus;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getRefundCnyAmt() {
		return refundCnyAmt;
	}
	public void setRefundCnyAmt(String refundCnyAmt) {
		this.refundCnyAmt = refundCnyAmt;
	}
	public String getBankList() {
		return bankList;
	}
	public void setBankList(String bankList) {
		this.bankList = bankList;
	}
	private Map info;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public Map getInfo() {
		return info;
	}
	public void setInfo(Map info) {
		this.info = info;
	}
	public String getTradeState() {
		return tradeState;
	}
	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

}
