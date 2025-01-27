package com.gl.mdr.feature.stolenImeiStatusCheck.model;

public class StolenImeiStatusCheckRequest {

    private String createdOn;
    private String modifiedOn;
    private String imei1;
    private String imei2;
    private String imei3;
    private String imei4;
    private String transactionId;
    private String requestMode;
    private String fileName;
    private Integer fileRecordCount;
    private Integer countFoundInLost;
    private String status;
    private String remark;
    private String requestId;
    private String contactNumber;

    private Integer featureId;
    private Integer userId;
    private String userName;

    public String userType;
    private String publicIp;
    private String browser;
    private String searchString;
    private Integer userTypeId;


    private String orderColumnName;
    private String order;
    private String sort;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
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

    public Integer getCountFoundInLost() {
        return countFoundInLost;
    }

    public void setCountFoundInLost(Integer countFoundInLost) {
        this.countFoundInLost = countFoundInLost;
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

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei3() {
        return imei3;
    }

    public void setImei3(String imei3) {
        this.imei3 = imei3;
    }

    public String getImei4() {
        return imei4;
    }

    public void setImei4(String imei4) {
        this.imei4 = imei4;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileRecordCount() {
        return fileRecordCount;
    }

    public void setFileRecordCount(Integer fileRecordCount) {
        this.fileRecordCount = fileRecordCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StolenImeiStatusCheckRequest{" +
                "createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                ", imei1='" + imei1 + '\'' +
                ", imei2='" + imei2 + '\'' +
                ", imei3='" + imei3 + '\'' +
                ", imei4='" + imei4 + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileRecordCount=" + fileRecordCount +
                ", countFoundInLost=" + countFoundInLost +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", requestId='" + requestId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", userId=" + userId +
                ", userType='" + userType + '\'' +
                ", publicIp='" + publicIp + '\'' +
                ", browser='" + browser + '\'' +
                ", searchString='" + searchString + '\'' +
                ", featureId=" + featureId +
                ", userTypeId=" + userTypeId +
                ", userName='" + userName + '\'' +
                ", orderColumnName='" + orderColumnName + '\'' +
                ", order='" + order + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
