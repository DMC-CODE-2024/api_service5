package com.gl.mdr.model.file;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class StolenPoliceVerificationDeviceFileModel {

	@CsvBindByName(column = "Creation Date")
	@CsvBindByPosition(position = 0)
	private LocalDateTime createdOn;
	
	@CsvBindByName(column = "Update Date")
	@CsvBindByPosition(position = 1)
	private LocalDateTime modifiedOn;
	
	@CsvBindByName(column = "Owner DOB")
	@CsvBindByPosition(position = 2)
	private String ownerDOB;
	
	
	@CsvBindByName(column = "contact number")
	@CsvBindByPosition(position = 3)
	private String contactNumber;
	

	@CsvBindByName(column = "IMEI")
	@CsvBindByPosition(position = 4) 
	private String imei1;
	
	@CsvBindByName(column = "Brand Name")
	@CsvBindByPosition(position = 5)
	private String deviceBrand;
	
	@CsvBindByName(column = "Model Name")
	@CsvBindByPosition(position = 6)
	private String deviceModel;
	
	@CsvBindByName(column = "Invoice URL")
	@CsvBindByPosition(position = 7) 
	private String devicePurchaseInvoiceUrl;
	
	@CsvBindByName(column = "Device Lost Date")
	@CsvBindByPosition(position = 8)
	private String deviceLostDdateTime;

	@CsvBindByName(column = "Device Owner Name")
	@CsvBindByPosition(position = 9)
	private String deviceOwnerName;
	
	@CsvBindByName(column = "Device Owner Email")
	@CsvBindByPosition(position = 10)
	private String deviceOwnerEmail;
	
	@CsvBindByName(column = "Device Owner Address")
	@CsvBindByPosition(position = 10)
	private String deviceOwnerAddress;
	
	
	@CsvBindByName(column = "Remarks")
	@CsvBindByPosition(position = 11)
	private String remarks;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 12)
	private String status;
	
	@CsvBindByName(column = "Request Type")
	@CsvBindByPosition(position = 13)
	private String requestType;
	
	 
	@CsvBindByName(column = "Request ID")
	@CsvBindByPosition(position = 14)
	private String requestId;


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


	public String getDeviceLostDdateTime() {
		return deviceLostDdateTime;
	}


	public void setDeviceLostDdateTime(String deviceLostDdateTime) {
		this.deviceLostDdateTime = deviceLostDdateTime;
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
	
	

	
	
}
