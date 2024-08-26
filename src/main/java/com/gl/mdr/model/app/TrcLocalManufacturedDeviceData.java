package com.gl.mdr.model.app;

import java.sql.Timestamp;
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
@Table(name = "trc_local_manufactured_device_data")
public class TrcLocalManufacturedDeviceData {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "imei", unique = true, nullable = false)
    private String imei;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "manufacturer_id", nullable = false)
    private String manufacturerId;

    @Column(name = "manufacturer_name", nullable = false)
    private String manufacturerName;

    @Column(name = "manufacturering_date", nullable = false)
    private String manufacturingDate;

    @Column(name = "actual_imei")
    private String actualImei;

    @Column(name = "tac")
    private String tac;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
    
    @Transient
    private String tableName=Tags.trc_local_manufactured_device_data;
    

	
    
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

	

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(String manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public String getActualImei() {
		return actualImei;
	}

	public void setActualImei(String actualImei) {
		this.actualImei = actualImei;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrcLocalManufacturedDeviceData [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", serialNumber=");
		builder.append(serialNumber);
		builder.append(", manufacturerId=");
		builder.append(manufacturerId);
		builder.append(", manufacturerName=");
		builder.append(manufacturerName);
		builder.append(", manufacturingDate=");
		builder.append(manufacturingDate);
		builder.append(", actualImei=");
		builder.append(actualImei);
		builder.append(", tac=");
		builder.append(tac);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append("]");
		return builder.toString();
	}
    
    
}
