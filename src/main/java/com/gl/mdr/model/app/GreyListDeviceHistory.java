package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "grey_list_his")
public class GreyListDeviceHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "actual_imei")
	private String actualImei;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "created_on",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn;

	@Column(name = "imei")
	private String imei;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "operation")
	private Integer operation;

	@Column(name = "operator_id")
	private String operatorId;

	@Column(name = "operator_name")
	private String operatorName;

	@Column(name = "mode_type")
	private String modeType;

	@Column(name = "modified_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime  modifiedOn;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "txn_id")
	private String txnId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "tac")
	private String tac;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "source")
	private String source;

	@Column(name = "expiry_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime expiryDate;

	@Column(name = "complaint_type")
	private String complaintType;
    
    @Transient
    private String tableName=Tags.grey_list_his;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActualImei() {
		return actualImei;
	}

	public void setActualImei(String actualImei) {
		this.actualImei = actualImei;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "GreyListDeviceHistory{" +
				"id=" + id +
				", actualImei='" + actualImei + '\'' +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", operation=" + operation +
				", operatorId='" + operatorId + '\'' +
				", operatorName='" + operatorName + '\'' +
				", modeType='" + modeType + '\'' +
				", modifiedOn=" + modifiedOn +
				", requestType='" + requestType + '\'' +
				", txnId='" + txnId + '\'' +
				", userId='" + userId + '\'' +
				", userType='" + userType + '\'' +
				", tac='" + tac + '\'' +
				", remarks='" + remarks + '\'' +
				", source='" + source + '\'' +
				", expiryDate=" + expiryDate +
				", complaintType='" + complaintType + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
