package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "imei_pair_detail")
public class ImeiPairDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "pairing_date")
    private LocalDateTime pairingDate;

    @Column(name = "record_time")
    private LocalDateTime recordTime;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "imei")
    private String imei;

    @Column(name = "imsi")
    private String imsi;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "Operator")
    private String operator;

    @Column(name = "allowed_days")
    private Integer allowedDays;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "pair_mode")
    private String pairMode;

    @Column(name = "actual_imei")
    private String actualImei;

    @Column(name = "txn_id")
    private String txnId;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
    
    @Transient
    private String tableName=Tags.imei_pair_detail;
    
    
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public LocalDateTime getPairingDate() {
		return pairingDate;
	}

	public void setPairingDate(LocalDateTime pairingDate) {
		this.pairingDate = pairingDate;
	}

	public LocalDateTime getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(LocalDateTime recordTime) {
		this.recordTime = recordTime;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getAllowedDays() {
		return allowedDays;
	}

	public void setAllowedDays(Integer allowedDays) {
		this.allowedDays = allowedDays;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPairMode() {
		return pairMode;
	}

	public void setPairMode(String pairMode) {
		this.pairMode = pairMode;
	}

	public String getActualImei() {
		return actualImei;
	}

	public void setActualImei(String actualImei) {
		this.actualImei = actualImei;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImeiPairDetail [id=");
		builder.append(id);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", pairingDate=");
		builder.append(pairingDate);
		builder.append(", recordTime=");
		builder.append(recordTime);
		builder.append(", msisdn=");
		builder.append(msisdn);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", imsi=");
		builder.append(imsi);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", allowedDays=");
		builder.append(allowedDays);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", pairMode=");
		builder.append(pairMode);
		builder.append(", actualImei=");
		builder.append(actualImei);
		builder.append(", txnId=");
		builder.append(txnId);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append("]");
		return builder.toString();
	}

	
}
