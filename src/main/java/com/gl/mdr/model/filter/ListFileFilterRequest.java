package com.gl.mdr.model.filter;

import com.gl.mdr.bulk.imei.entity.AuditTrailModel;

public class ListFileFilterRequest {
	private Long id;
	public String startDate;
	public String endDate;
	public String createdOn;
	public String modifiedOn;
	private String fileName;
	private String fileType;
	private String listType;
	private String updatedBy;
	private String status;
	public Integer userId;
	public String userType;
	private String publicIp;
	private String browser;
	private String searchString;
	private Long featureId;
	private Integer userTypeId;
	private String orderColumnName;
	private String order;
	private String sort;
	private String operator;
	private String requestNo;
	private String policeId;
	private String email;
	private String operatorName;
	public AuditTrailModel auditTrailModel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
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
	public Long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Long featureId) {
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPoliceId() {
		return policeId;
	}
	public void setPoliceId(String policeId) {
		this.policeId = policeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AuditTrailModel getAuditTrailModel() {
		return auditTrailModel;
	}
	public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
		this.auditTrailModel = auditTrailModel;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Override
	public String toString() {
		return "ListFileFilterRequest [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + ", fileName=" + fileName + ", fileType=" + fileType
				+ ", listType=" + listType + ", updatedBy=" + updatedBy + ", status=" + status + ", userId=" + userId
				+ ", userType=" + userType + ", publicIp=" + publicIp + ", browser=" + browser + ", searchString="
				+ searchString + ", featureId=" + featureId + ", userTypeId=" + userTypeId + ", orderColumnName="
				+ orderColumnName + ", order=" + order + ", sort=" + sort + ", operator=" + operator + ", requestNo="
				+ requestNo + ", policeId=" + policeId + ", email=" + email + ", operatorName=" + operatorName
				+ ", auditTrailModel=" + auditTrailModel + "]";
	}
	

	
}
