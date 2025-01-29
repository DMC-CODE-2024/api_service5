package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "duplicate_device_detail")
public class DuplicateDeviceDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_on")
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime createdOn;
	
	@Column(name="modified_on")
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modifiedOn;

	private String imei;
	private String imsi;
	private String msisdn;
	private String fileName;

	@Column(name="edr_time")
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime edrTime;

	private String operator;

	@Column(name="expiryDate")
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime expiryDate;

	@Transient
	private String uploadedFilePath;

	private String remark;
	private String updatedBy;
	private String transactionId;
	//private String documentType1;
	//private String documentType2;
	//private String documentType3;
	//private String documentType4;

	/*@Column(name="document_file_name_1", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName1;

	@Column(name="document_file_name_2", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName2;

	@Column(name="document_file_name_3", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName3;

	@Column(name="document_file_name_4", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName4;*/
	//private String approveTransactionId;
	//private String approveRemark;
	//private int reminderStatus;
	//private int successCount;
	//private int failCount;

	private String status;
	@Transient
	private String interpretation;
	
	//private Integer userStatus;
	private String redmineTktId;

	@Transient
    private String tableName=Tags.duplicate_device_detail;

	public String getRedmineTktId() {
		return redmineTktId;
	}

	public void setRedmineTktId(String redmineTktId) {
		this.redmineTktId = redmineTktId;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public LocalDateTime getEdrTime() {
		return edrTime;
	}
	public void setEdrTime(LocalDateTime edrTime) {
		this.edrTime = edrTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getUploadedFilePath() {
		return uploadedFilePath;
	}
	public void setUploadedFilePath(String uploadedFilePath) {
		this.uploadedFilePath = uploadedFilePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	
	public String getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	@Override
	public String toString() {
		return "DuplicateDeviceDetail{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", modifiedOn=" + modifiedOn +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", fileName='" + fileName + '\'' +
				", edrTime=" + edrTime +
				", operator='" + operator + '\'' +
				", expiryDate=" + expiryDate +
				", uploadedFilePath='" + uploadedFilePath + '\'' +
				", remark='" + remark + '\'' +
				", updatedBy='" + updatedBy + '\'' +
				", transactionId='" + transactionId + '\'' +
				", status='" + status + '\'' +
				", interpretation='" + interpretation + '\'' +
				", redmineTktId='" + redmineTktId + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
