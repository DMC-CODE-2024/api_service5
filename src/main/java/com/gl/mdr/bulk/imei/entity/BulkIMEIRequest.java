package com.gl.mdr.bulk.imei.entity;

public class BulkIMEIRequest {
	public String requestMode;
    public String email;
    public String contactNumber;
    public String fileName;
    public String TransactionId;
    public String otp;
    public String addedBy;
    public String requestType;
    public String lang;
	/*
	 * public String ip; public String browser; public String userAgent;
	 */
    
    public AuditTrailModel auditTrailModel;
	public String getRequestMode() {
		return requestMode;
	}
	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public AuditTrailModel getAuditTrailModel() {
		return auditTrailModel;
	}
	public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
		this.auditTrailModel = auditTrailModel;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	@Override
	public String toString() {
		return "BulkIMEIRequest [requestMode=" + requestMode + ", email=" + email + ", contactNumber=" + contactNumber
				+ ", fileName=" + fileName + ", TransactionId=" + TransactionId + ", otp=" + otp + ", addedBy="
				+ addedBy + ", requestType=" + requestType + ", lang=" + lang + ", auditTrailModel=" + auditTrailModel
				+ "]";
	}
	
	
	
	
}
