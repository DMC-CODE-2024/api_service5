package com.gl.mdr.model.file;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class TrackLostDeviceFileModel {
	
	@CsvBindByName(column = "Date")
	@CsvBindByPosition(position = 0)
	private LocalDateTime createdOn;
	
	@CsvBindByName(column = "Request Number")
	@CsvBindByPosition(position = 1)
	private String requestNo;
	
	@CsvBindByName(column = "IMEI")
	@CsvBindByPosition(position = 2)
	private String imei;
	
	@CsvBindByName(column = "IMSI")
	@CsvBindByPosition(position = 3)
	private String imsi;
	
	@CsvBindByName(column = "MSISDN")
	@CsvBindByPosition(position = 4)
	private String msisdn;
	
	@CsvBindByName(column = "Operator")
	@CsvBindByPosition(position = 5)
	private String operator;
	
	@CsvBindByName(column = "Request Type")
	@CsvBindByPosition(position = 6)
	private String requestType;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 7)
	private String status;
	
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public String getRequestNo() {
		return requestNo;
	}


	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
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


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getImsi() {
		return imsi;
	}


	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	

	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	@Override
	public String toString() {
		return "TrackLostDeviceFileModel [createdOn=" + createdOn + ", requestNo=" + requestNo + ", imei=" + imei
				+ ", imsi=" + imsi + ", msisdn=" + msisdn + ", operator=" + operator + ", status=" + status + "]";
	}

	
	
}
