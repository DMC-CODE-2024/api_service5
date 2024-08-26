package com.gl.mdr.model.filter;

public class SearchIMEIRequest {
	private String imei;
	private String msisdn;
	private String requestId;
	
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
	
	private Long id;
	private String tableName;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	@Override
	public String toString() {
		return "SearchIMEIRequest{" +
				"imei='" + imei + '\'' +
				", msisdn='" + msisdn + '\'' +
				", requestId='" + requestId + '\'' +
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
				", id=" + id +
				", tableName='" + tableName + '\'' +
				'}';
	}

}
