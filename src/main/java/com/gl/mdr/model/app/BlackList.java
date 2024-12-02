package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "black_list")
public class BlackList {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;
		
		/*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "modified_on")
	    private LocalDateTime modifiedOn;*/

	    /*@Column(name = "complaint_type")
	    private String complaintType;*/

	    /*@Column(name = "expiry_date")
	    private LocalDateTime expiryDate;*/

	    @Column(name = "imei")
	    private String imei;

	    /*@Column(name = "mode_type")
	    private String modeType;*/

	    /*@Column(name = "request_type")
	    private String requestType;*/

	    /*@Column(name = "txn_id")
	    private String txnId;

	    @Column(name = "user_id")
	    private String userId;

	    @Column(name = "user_type")
	    private String userType;

	    @Column(name = "operator_id")
	    private String operatorId;

	    @Column(name = "operator_name")
	    private String operatorName;

	    @Column(name = "actual_imei")
	    private String actualImei;

	    @Column(name = "tac")
	    private String tac;

	    @Column(name = "remark")
	    private String remark;*/

	    @Column(name = "imsi")
	    private String imsi;

	    @Column(name = "msisdn")
	    private String msisdn;

	    @Column(name = "source")
	    private String source;
	    
	    @Transient
	    private String tableName=Tags.black_list;
	    
	    
	    
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

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}


	@Override
	public String toString() {
		return "BlackList{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", source='" + source + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
