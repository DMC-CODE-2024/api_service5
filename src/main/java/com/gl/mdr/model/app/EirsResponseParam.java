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
@Table(name = "eirs_response_param")
public class EirsResponseParam {
	
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
	
	@Column(name="description", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String description = "";
	


	@Column(name="active", length=1, columnDefinition="int(1) DEFAULT '1'")
	private int active ;
	
	@Column(name="tag", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String tag = "";
	
	@Column(name="value", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String value = "";
	
	@Column(name="feature_name", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String feature_name = "";
	
	@Column(name="remark", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String remark = "";
	
	@Column(name="user_type", length=50, columnDefinition="varchar(50) DEFAULT ''")
	private String userType = "";
	
	@Column(name="modified_by", length=50, columnDefinition="varchar(50) DEFAULT ''")
	private String modifiedBy = "";
	
	@Column(name="language", length=10, columnDefinition="varchar(10) DEFAULT ''")
	private String language = "";

	@Column(name="subject", length=200, columnDefinition="varchar(200) DEFAULT ''")
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


}
