package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "black_list_his")
public class BlackListHistory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	  /*  @Column(name = "actual_imei")
	    private String actualImei;*/
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
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

	    /*@Column(name = "operator_id")
	    private String operatorId;

	    @Column(name = "operator_name")
	    private String operatorName;

	    @Column(name = "complaint_type")
	    private String complaintType;

	    @Column(name = "expiry_date")
	    private String expiryDate;

	    @Column(name = "mode_type")
	    private String modeType;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
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
*/
	    @Column(name = "source")
	    private String source;

	   /* @Column(name = "action")
	    private String action;

	    @Column(name = "action_remark")
	    private String actionRemark;*/
	    
	    @Transient
	    private String tableName=Tags.black_list_his;

		@Transient
		private String operationInterp;

		public String getOperationInterp() {
			return operationInterp;
		}

	public void setOperationInterp(String operationInterp) {
		this.operationInterp = operationInterp;
	}

	public String getSource() { return source; }

		public void setSource(String source) { this.source = source;}

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


	@Override
	public String toString() {
		return "BlackListHistory{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", operation=" + operation +
				", source='" + source + '\'' +
				", tableName='" + tableName + '\'' +
				", operationInterp='" + operationInterp + '\'' +
				'}';
	}
}
