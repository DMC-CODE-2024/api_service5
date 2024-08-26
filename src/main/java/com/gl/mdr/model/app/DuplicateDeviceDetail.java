package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.mdr.model.constants.Tags;


@Entity
@Table(name = "duplicate_device_detail")
public class DuplicateDeviceDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;

	@Column(name="modified_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;

	private String imei;
	private String imsi;
	private String msisdn;
	private String fileName;

	@Column(name="edr_time", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime edrTime;

	private String operator;

	@Column(name="expiryDate", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expiryDate;

	@Transient
	private String uploadedFilePath;

	private String remarks;
	private String updatedBy;
	private String transactionId;
	private String documentType1;
	private String documentType2;
	private String documentType3;
	private String documentType4;

	@Column(name="document_file_name_1", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName1;

	@Column(name="document_file_name_2", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName2;

	@Column(name="document_file_name_3", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName3;

	@Column(name="document_file_name_4", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName4;
	private String approveTransactionId;
	private String approveRemark;
	private int reminderStatus;
	private int successCount;
	private int failCount;

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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getDocumentType1() {
		return documentType1;
	}
	public void setDocumentType1(String documentType1) {
		this.documentType1 = documentType1;
	}
	public String getDocumentType2() {
		return documentType2;
	}
	public void setDocumentType2(String documentType2) {
		this.documentType2 = documentType2;
	}
	public String getDocumentType3() {
		return documentType3;
	}
	public void setDocumentType3(String documentType3) {
		this.documentType3 = documentType3;
	}
	public String getDocumentType4() {
		return documentType4;
	}
	public void setDocumentType4(String documentType4) {
		this.documentType4 = documentType4;
	}
	public String getDocumentFileName1() {
		return documentFileName1;
	}
	public void setDocumentFileName1(String documentFileName1) {
		this.documentFileName1 = documentFileName1;
	}
	public String getDocumentFileName2() {
		return documentFileName2;
	}
	public void setDocumentFileName2(String documentFileName2) {
		this.documentFileName2 = documentFileName2;
	}
	public String getDocumentFileName3() {
		return documentFileName3;
	}
	public void setDocumentFileName3(String documentFileName3) {
		this.documentFileName3 = documentFileName3;
	}
	public String getDocumentFileName4() {
		return documentFileName4;
	}
	public void setDocumentFileName4(String documentFileName4) {
		this.documentFileName4 = documentFileName4;
	}
	public String getApproveTransactionId() {
		return approveTransactionId;
	}
	public void setApproveTransactionId(String approveTransactionId) {
		this.approveTransactionId = approveTransactionId;
	}
	public String getApproveRemark() {
		return approveRemark;
	}
	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}
	public int getReminderStatus() {
		return reminderStatus;
	}
	public void setReminderStatus(int reminderStatus) {
		this.reminderStatus = reminderStatus;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
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
				", remarks='" + remarks + '\'' +
				", updatedBy='" + updatedBy + '\'' +
				", transactionId='" + transactionId + '\'' +
				", documentType1='" + documentType1 + '\'' +
				", documentType2='" + documentType2 + '\'' +
				", documentType3='" + documentType3 + '\'' +
				", documentType4='" + documentType4 + '\'' +
				", documentFileName1='" + documentFileName1 + '\'' +
				", documentFileName2='" + documentFileName2 + '\'' +
				", documentFileName3='" + documentFileName3 + '\'' +
				", documentFileName4='" + documentFileName4 + '\'' +
				", approveTransactionId='" + approveTransactionId + '\'' +
				", approveRemark='" + approveRemark + '\'' +
				", reminderStatus=" + reminderStatus +
				", successCount=" + successCount +
				", failCount=" + failCount +
				", status='" + status + '\'' +
				", interpretation='" + interpretation + '\'' +
				", redmineTktId='" + redmineTktId + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
