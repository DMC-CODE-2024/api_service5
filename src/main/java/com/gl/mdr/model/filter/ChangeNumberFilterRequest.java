package com.gl.mdr.model.filter;

public class ChangeNumberFilterRequest {
	private Long id;
	private String status;
	public Integer userId;
	public String userType;
	private String publicIp;
	private String browser;
	private String msisdn;
	private String requestNo;
	private String email;
	private String remarks;
	private String lang;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	@Override
	public String toString() {
		return "ChangeNumberFilterRequest [id=" + id + ", status=" + status + ", userId=" + userId + ", userType="
				+ userType + ", publicIp=" + publicIp + ", browser=" + browser + ", msisdn=" + msisdn + ", requestNo="
				+ requestNo + ", email=" + email + ", remarks=" + remarks + ", lang=" + lang + "]";
	}
	
	
	
}
