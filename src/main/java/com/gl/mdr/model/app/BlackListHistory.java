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
@Table(name = "black_list_his")
public class BlackListHistory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "actual_imei")
	    private String actualImei;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
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

	    @Column(name = "complaint_type")
	    private String complaintType;

	    @Column(name = "expiry_date")
	    private String expiryDate;

	    @Column(name = "mode_type")
	    private String modeType;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "modified_on")
	    private LocalDateTime modifiedOn;

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

	    @Column(name = "action")
	    private String action;

	    @Column(name = "action_remark")
	    private String actionRemark;
	    
	    @Transient
	    private String tableName=Tags.blacklist_device_his;
	    
	    
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

		public String getComplaintType() {
			return complaintType;
		}

		public void setComplaintType(String complaintType) {
			this.complaintType = complaintType;
		}

		public String getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("BlackListHistory [id=");
			builder.append(id);
			builder.append(", actualImei=");
			builder.append(actualImei);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", imei=");
			builder.append(imei);
			builder.append(", imsi=");
			builder.append(imsi);
			builder.append(", msisdn=");
			builder.append(msisdn);
			builder.append(", operation=");
			builder.append(operation);
			builder.append(", operatorId=");
			builder.append(operatorId);
			builder.append(", operatorName=");
			builder.append(operatorName);
			builder.append(", complaintType=");
			builder.append(complaintType);
			builder.append(", expiryDate=");
			builder.append(expiryDate);
			builder.append(", modeType=");
			builder.append(modeType);
			builder.append(", modifiedOn=");
			builder.append(modifiedOn);
			builder.append(", requestType=");
			builder.append(requestType);
			builder.append(", txnId=");
			builder.append(txnId);
			builder.append(", userId=");
			builder.append(userId);
			builder.append(", userType=");
			builder.append(userType);
			builder.append(", tac=");
			builder.append(tac);
			builder.append(", remarks=");
			builder.append(remarks);
			builder.append(", source=");
			builder.append(source);
			builder.append(", action=");
			builder.append(action);
			builder.append(", actionRemark=");
			builder.append(actionRemark);
			builder.append(", tableName=");
			builder.append(tableName);
			builder.append("]");
			return builder.toString();
		}

		
	    
}
