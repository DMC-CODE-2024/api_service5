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

	    @Column(name = "operation")
	    private Integer operation;

	    @Column(name = "status", length = 50)
	    private String status;

	    @Column(name = "device_brand", length = 50)
	    private String deviceBrand;

	    @Column(name = "device_model", length = 50)
	    private String deviceModel;

	    @Column(name = "contact_number", length = 20)
	    private String contactNumber;

	    @Column(name = "lost_stolen_request_id", length = 20)
	    private String lostStolenRequestId;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "modified_on")
	    private LocalDateTime modifiedOn;
	    
	    @Transient
	    private String tableName=Tags.lost_device_detail_his;
	    
	    
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

		public Integer getOperation() {
			return operation;
		}

		public void setOperation(Integer operation) {
			this.operation = operation;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDeviceBrand() {
			return deviceBrand;
		}

		public void setDeviceBrand(String deviceBrand) {
			this.deviceBrand = deviceBrand;
		}

		public String getDeviceModel() {
			return deviceModel;
		}

		public void setDeviceModel(String deviceModel) {
			this.deviceModel = deviceModel;
		}

		public String getContactNumber() {
			return contactNumber;
		}

		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}

		public String getLostStolenRequestId() {
			return lostStolenRequestId;
		}

		public void setLostStolenRequestId(String lostStolenRequestId) {
			this.lostStolenRequestId = lostStolenRequestId;
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
			builder.append("LostDeviceDetailHistory [id=");
			builder.append(id);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", imei=");
			builder.append(imei);
			builder.append(", requestType=");
			builder.append(requestType);
			builder.append(", requestId=");
			builder.append(requestId);
			builder.append(", operation=");
			builder.append(operation);
			builder.append(", status=");
			builder.append(status);
			builder.append(", deviceBrand=");
			builder.append(deviceBrand);
			builder.append(", deviceModel=");
			builder.append(deviceModel);
			builder.append(", contactNumber=");
			builder.append(contactNumber);
			builder.append(", lostStolenRequestId=");
			builder.append(lostStolenRequestId);
			builder.append(", modifiedOn=");
			builder.append(modifiedOn);
			builder.append(", tableName=");
			builder.append(tableName);
			builder.append("]");
			return builder.toString();
		}

		
	    
}
