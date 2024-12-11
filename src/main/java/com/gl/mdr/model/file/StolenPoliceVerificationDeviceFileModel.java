package com.gl.mdr.model.file;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class StolenPoliceVerificationDeviceFileModel {

	@CsvBindByName(column = "Creation Date")
	@CsvBindByPosition(position = 0)
	private LocalDateTime createdOn;
	
	@CsvBindByName(column = "Request Number")
	@CsvBindByPosition(position = 1)
	private String requestId;
	
	@CsvBindByName(column = "IMEI")
	@CsvBindByPosition(position = 2)
	private String imei1;
	
	
	@CsvBindByName(column = " Uploaded by")
	@CsvBindByPosition(position = 3)
	private String  uploadedBy;
	

	@CsvBindByName(column = " Request Mode")
	@CsvBindByPosition(position = 4) 
	private String requestMode;
	
	@CsvBindByName(column = " Request Type")
	@CsvBindByPosition(position = 5)
	private String requestType;
	
	@CsvBindByName(column = "Province")
	@CsvBindByPosition(position = 6)
	private String province;
	
	@CsvBindByName(column = "District")
	@CsvBindByPosition(position = 7) 
	private String district;
	
	@CsvBindByName(column = "Commune")
	@CsvBindByPosition(position = 8)
	private String commune;

	@CsvBindByName(column = "Device Type")
	@CsvBindByPosition(position = 9)
	private String deviceType;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 10)
	private String userStatus;

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getImei1() {
		return imei1;
	}

	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}

	
	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
	
	



	
	
}
