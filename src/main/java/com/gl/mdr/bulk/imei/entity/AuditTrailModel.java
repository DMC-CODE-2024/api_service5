package com.gl.mdr.bulk.imei.entity;

public class AuditTrailModel {
	
	public String roleType;
    public int featureId;
    public int userTypeId;
    public String userType;
    public String userName;
    public int userId;
    public int type;
    public String browser;
    public String publicIp;
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public int getFeatureId() {
		return featureId;
	}
	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	@Override
	public String toString() {
		return "AuditTrailModel [roleType=" + roleType + ", featureId=" + featureId + ", userTypeId=" + userTypeId
				+ ", userType=" + userType + ", userName=" + userName + ", userId=" + userId + ", type=" + type
				+ ", browser=" + browser + ", publicIp=" + publicIp + "]";
	}
    
}
