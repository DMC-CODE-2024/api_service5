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
@Table(name = "imei_pair_detail_his")
public class ImeiPairDetailHistory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id")
	    private Integer id;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;

	    @Column(name = "allowed_days")
	    private Integer allowedDays;

	    @Column(name = "imei")
	    private String imei;

	    @Column(name = "imsi")
	    private String imsi;

	    @Column(name = "msisdn")
	    private String msisdn;

	    @Column(name = "pairing_date")
	    private String pairingDate;

	    @Column(name = "record_time")
	    private String recordTime;

	    @Column(name = "file_name")
	    private String fileName;

	    @Column(name = "gsma_status")
	    private String gsmaStatus;

	    @Column(name = "pair_mode")
	    private String pairMode;

	    @Column(name = "operator")
	    private String operator;

	    @Column(name = "action")
	    private String action;

	    @Column(name = "action_remark")
	    private String actionRemark;

	    @Column(name = "expiry_date")
	    private String expiryDate;

	    @Column(name = "actual_imei")
	    private String actualImei;

	    @Column(name = "txn_id")
	    private String txnId;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "modified_on")
	    private LocalDateTime modifiedOn;
	    
	    @Transient
	    private String tableName=Tags.imei_pair_detail_his;
	    
	    
	    
		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		
		public LocalDateTime getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
		}

		public void setModifiedOn(LocalDateTime modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		public Integer getAllowedDays() {
			return allowedDays;
		}

		public void setAllowedDays(Integer allowedDays) {
			this.allowedDays = allowedDays;
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

		public String getPairingDate() {
			return pairingDate;
		}

		public void setPairingDate(String pairingDate) {
			this.pairingDate = pairingDate;
		}

		public String getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(String recordTime) {
			this.recordTime = recordTime;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getGsmaStatus() {
			return gsmaStatus;
		}

		public void setGsmaStatus(String gsmaStatus) {
			this.gsmaStatus = gsmaStatus;
		}

		public String getPairMode() {
			return pairMode;
		}

		public void setPairMode(String pairMode) {
			this.pairMode = pairMode;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getActionRemark() {
			return actionRemark;
		}

		public void setActionRemark(String actionRemark) {
			this.actionRemark = actionRemark;
		}

		public String getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ImeiPairDetailHistory [id=");
			builder.append(id);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", allowedDays=");
			builder.append(allowedDays);
			builder.append(", imei=");
			builder.append(imei);
			builder.append(", imsi=");
			builder.append(imsi);
			builder.append(", msisdn=");
			builder.append(msisdn);
			builder.append(", pairingDate=");
			builder.append(pairingDate);
			builder.append(", recordTime=");
			builder.append(recordTime);
			builder.append(", fileName=");
			builder.append(fileName);
			builder.append(", gsmaStatus=");
			builder.append(gsmaStatus);
			builder.append(", pairMode=");
			builder.append(pairMode);
			builder.append(", operator=");
			builder.append(operator);
			builder.append(", action=");
			builder.append(action);
			builder.append(", actionRemark=");
			builder.append(actionRemark);
			builder.append(", expiryDate=");
			builder.append(expiryDate);
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
