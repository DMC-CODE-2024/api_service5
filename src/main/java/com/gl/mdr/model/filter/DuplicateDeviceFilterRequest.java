package com.gl.mdr.model.filter;

public class DuplicateDeviceFilterRequest {
	private Long id;
	public String createdOn;
	public String modifiedOn;
	
	private String imei;
	private String msisdn;
	private String edrTime;
	private String redmineTktId;
	private String updatedBy;
	private String status;
	
	
	public Integer userId;
	public String userType;
	private String publicIp;
	private String browser;
	private String searchString;
	private Integer featureId;
	private Integer userTypeId;
	
	private String orderColumnName;
	private String order;
	private String sort;
	
	//Parameter when approving device
	private String transactionId;
	private String documentType1;
	private String documentType2;
	private String documentType3;
	private String documentType4;
	private String documentFileName1;
	private String documentFileName2;
	private String documentFileName3;
	private String documentFileName4;
	private String approveTransactionId;
	private String approveRemark;

	private String stateInterp;

	public String getRedmineTktId() {
		return redmineTktId;
	}

	public void setRedmineTktId(String redmineTktId) {
		this.redmineTktId = redmineTktId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getEdrTime() {
		return edrTime;
	}

	public void setEdrTime(String edrTime) {
		this.edrTime = edrTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getOrderColumnName() {
		return orderColumnName;
	}

	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDocumentType1() {
		return documentType1;
	}

	public void setDocumentType1(String documentType1) {
		this.documentType1 = documentType1;
	}

	public String getDocumentType2() {
		return documentType2;
	}

	public void setDocumentType2(String documentType2) {
		this.documentType2 = documentType2;
	}

	public String getDocumentType3() {
		return documentType3;
	}

	public void setDocumentType3(String documentType3) {
		this.documentType3 = documentType3;
	}

	public String getDocumentType4() {
		return documentType4;
	}

	public void setDocumentType4(String documentType4) {
		this.documentType4 = documentType4;
	}

	public String getDocumentFileName1() {
		return documentFileName1;
	}

	public void setDocumentFileName1(String documentFileName1) {
		this.documentFileName1 = documentFileName1;
	}

	public String getDocumentFileName2() {
		return documentFileName2;
	}

	public void setDocumentFileName2(String documentFileName2) {
		this.documentFileName2 = documentFileName2;
	}

	public String getDocumentFileName3() {
		return documentFileName3;
	}

	public void setDocumentFileName3(String documentFileName3) {
		this.documentFileName3 = documentFileName3;
	}

	public String getDocumentFileName4() {
		return documentFileName4;
	}

	public void setDocumentFileName4(String documentFileName4) {
		this.documentFileName4 = documentFileName4;
	}

	public String getApproveTransactionId() {
		return approveTransactionId;
	}

	public void setApproveTransactionId(String approveTransactionId) {
		this.approveTransactionId = approveTransactionId;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getStateInterp() {
		return stateInterp;
	}

	public void setStateInterp(String stateInterp) {
		this.stateInterp = stateInterp;
	}

	@Override
	public String toString() {
		return "DuplicateDeviceFilterRequest{" +
				"id=" + id +
				", createdOn='" + createdOn + '\'' +
				", modifiedOn='" + modifiedOn + '\'' +
				", imei='" + imei + '\'' +
				", msisdn='" + msisdn + '\'' +
				", edrTime='" + edrTime + '\'' +
				", redmineTktId='" + redmineTktId + '\'' +
				", updatedBy='" + updatedBy + '\'' +
				", status='" + status + '\'' +
				", userId=" + userId +
				", userType='" + userType + '\'' +
				", publicIp='" + publicIp + '\'' +
				", browser='" + browser + '\'' +
				", searchString='" + searchString + '\'' +
				", featureId=" + featureId +
				", userTypeId=" + userTypeId +
				", orderColumnName='" + orderColumnName + '\'' +
				", order='" + order + '\'' +
				", sort='" + sort + '\'' +
				", transactionId='" + transactionId + '\'' +
				", documentType1='" + documentType1 + '\'' +
				", documentType2='" + documentType2 + '\'' +
				", documentType3='" + documentType3 + '\'' +
				", documentType4='" + documentType4 + '\'' +
				", documentFileName1='" + documentFileName1 + '\'' +
				", documentFileName2='" + documentFileName2 + '\'' +
				", documentFileName3='" + documentFileName3 + '\'' +
				", documentFileName4='" + documentFileName4 + '\'' +
				", approveTransactionId='" + approveTransactionId + '\'' +
				", approveRemark='" + approveRemark + '\'' +
				", stateInterp='" + stateInterp + '\'' +
				'}';
	}
}
