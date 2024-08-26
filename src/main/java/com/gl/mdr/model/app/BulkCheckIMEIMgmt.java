package com.gl.mdr.model.app;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bulk_check_imei_mgmt"  )
public class BulkCheckIMEIMgmt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name="created_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name="modified_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedOn = LocalDateTime.now();
	
	@Column(name="file_name", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String fileName = "";
	
	@Column(name="status", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String status = "INIT";
	
	@Column(name="transaction_id", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String txnId = "";
	
	@Column(name="contact_number", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String contactNumber = "";
	
	@JsonIgnore @Column(name="otp", columnDefinition="varchar(6) DEFAULT ''")
	private String otp="";
	
	@Column(name="email", length=155, columnDefinition="varchar(255) DEFAULT ''")
	private String email = "";
	
	@Column(name="language", length=20, columnDefinition="varchar(20) DEFAULT 'english'")
	private String language;
	
	
	
	public BulkCheckIMEIMgmt() {}
	
	public BulkCheckIMEIMgmt(String fileName, String status, String otp, String txnId, String contactNumber) {
		this.fileName 	= fileName;
		this.status 	= status;
		this.otp 		= otp;
		this.txnId    	= txnId;
		this.txnId    	= txnId;
		this.contactNumber=contactNumber;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "BulkCheckIMEIMgmt [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", fileName="
				+ fileName + ", status=" + status + ", txnId=" + txnId + ", contactNumber=" + contactNumber + ", otp="
				+ otp + ", email=" + email + "]";
	}

	
	
}
