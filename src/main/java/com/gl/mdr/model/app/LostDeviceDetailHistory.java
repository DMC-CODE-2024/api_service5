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
@Table(name = "lost_device_detail_his")
//@Table(name = "stolen_device_detail_his")
public class LostDeviceDetailHistory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;

	    @Column(name = "imei", length = 20)
	    private String imei;

	    @Column(name = "request_type", length = 50)
	    private String requestType;

	    @Column(name = "request_id", length = 20)
	    private String requestId;

	    @Column(name = "status", length = 50)
	    private String status;

	    @Transient
	    private String tableName=Tags.lost_device_detail_his;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "LostDeviceDetailHistory{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", requestType='" + requestType + '\'' +
				", requestId='" + requestId + '\'' +
				", status='" + status + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
