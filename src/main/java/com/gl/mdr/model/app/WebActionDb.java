package com.gl.mdr.model.app;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "web_action_db")
public class WebActionDb {
	
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
	
	@Column(name="feature", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String feature = "";
	
	@Column(name="sub_feature", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String sub_feature = "";
	
	@Column(name="data", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String data = "";
	
	@Column(name="txn_id", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String txnId = "";
	
	@Column(name="state", length=155, columnDefinition="int(2) DEFAULT '1'")
	private int state ;
	
	@Column(name="retry_count", length=20, columnDefinition="int(2) DEFAULT '0'")
	private int retry_count;
	
	public WebActionDb() {}

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

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getSub_feature() {
		return sub_feature;
	}

	public void setSub_feature(String sub_feature) {
		this.sub_feature = sub_feature;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRetry_count() {
		return retry_count;
	}

	public void setRetry_count(int retry_count) {
		this.retry_count = retry_count;
	}

	
	
	
	
	
}
