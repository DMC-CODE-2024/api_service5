package com.gl.mdr.model.app;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "lost_device_detail" )
//@Table(name = "stolen_device_detail" )
public class LostDeviceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name="created_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();

	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime modified_on;

    @Column(name="imei")
    String imei;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "device_brand")
	private String deviceBrand;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name="request_id")
    String requestId;
    
    @Column(name="request_type")
    String requestType;
    

    @Column(name="lost_stolen_request_id")
    String lostStolenRequestId;
	
	@Column(name="status")
    String status;
	
	@Transient
    private String tableName=Tags.lost_device_detail;


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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getLostStolenRequestId() {
		return lostStolenRequestId;
	}

	public void setLostStolenRequestId(String lostStolenRequestId) {
		this.lostStolenRequestId = lostStolenRequestId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "LostDeviceDetail{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", requestId='" + requestId + '\'' +
				", requestType='" + requestType + '\'' +
				", lostStolenRequestId='" + lostStolenRequestId + '\'' +
				", status='" + status + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}