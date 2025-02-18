package com.gl.mdr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.mdr.bulk.imei.entity.AuditTrailModel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class StolenLostModelDto {

    private Long id;


    private int statusCode;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdOn;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime modifiedOn;


    private String ownerDOB;


    private String contactNumber;



    private String imei1;
    private String imei;

    private String imei2;


    private String imei3;


    private String imei4;


    private String deviceBrand;


    private String deviceModel;


    private String devicePurchaseInvoiceUrl;


    private String deviceLostDateTime;


    private String deviceOwnerName;


    private String deviceOwnerEmail;


    private String deviceOwnerAddress;


    private String deviceOwnerNationalIdUrl;


    private String deviceOwnerNationalID;


    private String deviceOwnerNationality;


    private String contactNumberForOtp;


    private String otp;


    private String firCopyUrl;


    private String remarks;


    private String status;


    private String createdBy;


    private String requestType;


    private String requestId;


    private String requestMode;


    private String fileName;


    private String fileRecordCount;


    private String mobileInvoiceBill;




    private String deviceOwnerAddress2;


    private String recoveryReason;


    private String province;


    private String district;


    private String commune;


    private String policeStation;


    private String passportNumber;


    private String otpEmail;


    private String category;


    private String userStatus;


    private String language;


    private String lostId;


    private String deviceOwnerState;


    private String deviceOwnerProvinceCity;


    private String deviceOwnerCommune;


    private int otpRetryCount;


    private String serialNumber;


    private String incidentDetail;


    private String otherDocument;


    private String deviceType;


    private String updatedBy;


    private String oldRequestId;


    private String browser;


    private String publicIp;


    private String userAgent;


    private String fileUrl;


    private AuditTrailModel auditTrailModel;


    private String lostDeviceImei;


    private String policeMgmtImei1;


    private String policeMgmtImei2;


    private String policeMgmtImei3;


    private String policeMgmtImei4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getOwnerDOB() {
        return ownerDOB;
    }

    public void setOwnerDOB(String ownerDOB) {
        this.ownerDOB = ownerDOB;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
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

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDevicePurchaseInvoiceUrl() {
        return devicePurchaseInvoiceUrl;
    }

    public void setDevicePurchaseInvoiceUrl(String devicePurchaseInvoiceUrl) {
        this.devicePurchaseInvoiceUrl = devicePurchaseInvoiceUrl;
    }

    public String getDeviceLostDateTime() {
        return deviceLostDateTime;
    }

    public void setDeviceLostDateTime(String deviceLostDateTime) {
        this.deviceLostDateTime = deviceLostDateTime;
    }

    public String getDeviceOwnerName() {
        return deviceOwnerName;
    }

    public void setDeviceOwnerName(String deviceOwnerName) {
        this.deviceOwnerName = deviceOwnerName;
    }

    public String getDeviceOwnerEmail() {
        return deviceOwnerEmail;
    }

    public void setDeviceOwnerEmail(String deviceOwnerEmail) {
        this.deviceOwnerEmail = deviceOwnerEmail;
    }

    public String getDeviceOwnerAddress() {
        return deviceOwnerAddress;
    }

    public void setDeviceOwnerAddress(String deviceOwnerAddress) {
        this.deviceOwnerAddress = deviceOwnerAddress;
    }

    public String getDeviceOwnerNationalIdUrl() {
        return deviceOwnerNationalIdUrl;
    }

    public void setDeviceOwnerNationalIdUrl(String deviceOwnerNationalIdUrl) {
        this.deviceOwnerNationalIdUrl = deviceOwnerNationalIdUrl;
    }

    public String getDeviceOwnerNationalID() {
        return deviceOwnerNationalID;
    }

    public void setDeviceOwnerNationalID(String deviceOwnerNationalID) {
        this.deviceOwnerNationalID = deviceOwnerNationalID;
    }

    public String getDeviceOwnerNationality() {
        return deviceOwnerNationality;
    }

    public void setDeviceOwnerNationality(String deviceOwnerNationality) {
        this.deviceOwnerNationality = deviceOwnerNationality;
    }

    public String getContactNumberForOtp() {
        return contactNumberForOtp;
    }

    public void setContactNumberForOtp(String contactNumberForOtp) {
        this.contactNumberForOtp = contactNumberForOtp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getFirCopyUrl() {
        return firCopyUrl;
    }

    public void setFirCopyUrl(String firCopyUrl) {
        this.firCopyUrl = firCopyUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getFileRecordCount() {
        return fileRecordCount;
    }

    public void setFileRecordCount(String fileRecordCount) {
        this.fileRecordCount = fileRecordCount;
    }

    public String getMobileInvoiceBill() {
        return mobileInvoiceBill;
    }

    public void setMobileInvoiceBill(String mobileInvoiceBill) {
        this.mobileInvoiceBill = mobileInvoiceBill;
    }

    public String getDeviceOwnerAddress2() {
        return deviceOwnerAddress2;
    }

    public void setDeviceOwnerAddress2(String deviceOwnerAddress2) {
        this.deviceOwnerAddress2 = deviceOwnerAddress2;
    }

    public String getRecoveryReason() {
        return recoveryReason;
    }

    public void setRecoveryReason(String recoveryReason) {
        this.recoveryReason = recoveryReason;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getOtpEmail() {
        return otpEmail;
    }

    public void setOtpEmail(String otpEmail) {
        this.otpEmail = otpEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLostId() {
        return lostId;
    }

    public void setLostId(String lostId) {
        this.lostId = lostId;
    }

    public String getDeviceOwnerState() {
        return deviceOwnerState;
    }

    public void setDeviceOwnerState(String deviceOwnerState) {
        this.deviceOwnerState = deviceOwnerState;
    }

    public String getDeviceOwnerProvinceCity() {
        return deviceOwnerProvinceCity;
    }

    public void setDeviceOwnerProvinceCity(String deviceOwnerProvinceCity) {
        this.deviceOwnerProvinceCity = deviceOwnerProvinceCity;
    }

    public String getDeviceOwnerCommune() {
        return deviceOwnerCommune;
    }

    public void setDeviceOwnerCommune(String deviceOwnerCommune) {
        this.deviceOwnerCommune = deviceOwnerCommune;
    }

    public int getOtpRetryCount() {
        return otpRetryCount;
    }

    public void setOtpRetryCount(int otpRetryCount) {
        this.otpRetryCount = otpRetryCount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIncidentDetail() {
        return incidentDetail;
    }

    public void setIncidentDetail(String incidentDetail) {
        this.incidentDetail = incidentDetail;
    }

    public String getOtherDocument() {
        return otherDocument;
    }

    public void setOtherDocument(String otherDocument) {
        this.otherDocument = otherDocument;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getOldRequestId() {
        return oldRequestId;
    }

    public void setOldRequestId(String oldRequestId) {
        this.oldRequestId = oldRequestId;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public AuditTrailModel getAuditTrailModel() {
        return auditTrailModel;
    }

    public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
        this.auditTrailModel = auditTrailModel;
    }

    public String getLostDeviceImei() {
        return lostDeviceImei;
    }

    public void setLostDeviceImei(String lostDeviceImei) {
        this.lostDeviceImei = lostDeviceImei;
    }

    public String getPoliceMgmtImei1() {
        return policeMgmtImei1;
    }

    public void setPoliceMgmtImei1(String policeMgmtImei1) {
        this.policeMgmtImei1 = policeMgmtImei1;
    }

    public String getPoliceMgmtImei2() {
        return policeMgmtImei2;
    }

    public void setPoliceMgmtImei2(String policeMgmtImei2) {
        this.policeMgmtImei2 = policeMgmtImei2;
    }

    public String getPoliceMgmtImei3() {
        return policeMgmtImei3;
    }

    public void setPoliceMgmtImei3(String policeMgmtImei3) {
        this.policeMgmtImei3 = policeMgmtImei3;
    }

    public String getPoliceMgmtImei4() {
        return policeMgmtImei4;
    }

    public void setPoliceMgmtImei4(String policeMgmtImei4) {
        this.policeMgmtImei4 = policeMgmtImei4;
    }


}
