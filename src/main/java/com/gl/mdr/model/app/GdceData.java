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
@Table(name = "gdce_data")
public class GdceData {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imei")
    private String imei;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "importer_id")
    private String importerId;

    @Column(name = "importer_name")
    private String importerName;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "date_of_actual_import")
    private LocalDateTime dateOfActualImport;

    @Column(name = "is_used")
    private Integer isUsed;

    @Column(name = "is_custom_tax_paid")
    private Integer isCustomTaxPaid;

    @Column(name = "actual_imei")
    private String actualImei;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "source")
    private String source;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "imei_status")
    private String imeiStatus;

    @Column(name = "status_remarks")
    private String statusRemarks;
    
    @Transient
    private String tableName=Tags.gdce_data;
    
	
    
   
    

    public LocalDateTime getCreatedOn() {
		return createdOn;
	}




	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}




	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}




	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}




	public LocalDateTime getDateOfActualImport() {
		return dateOfActualImport;
	}




	public void setDateOfActualImport(LocalDateTime dateOfActualImport) {
		this.dateOfActualImport = dateOfActualImport;
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




	




	public String getImporterId() {
		return importerId;
	}




	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}




	public String getImporterName() {
		return importerName;
	}




	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}




	public String getSerialNumber() {
		return serialNumber;
	}




	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}




	



	public Integer getIsUsed() {
		return isUsed;
	}




	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}




	public Integer getIsCustomTaxPaid() {
		return isCustomTaxPaid;
	}




	public void setIsCustomTaxPaid(Integer isCustomTaxPaid) {
		this.isCustomTaxPaid = isCustomTaxPaid;
	}




	public String getActualImei() {
		return actualImei;
	}




	public void setActualImei(String actualImei) {
		this.actualImei = actualImei;
	}




	public String getTransactionId() {
		return transactionId;
	}




	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}




	public String getSource() {
		return source;
	}




	public void setSource(String source) {
		this.source = source;
	}




	public String getRequestId() {
		return requestId;
	}




	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}




	public String getImeiStatus() {
		return imeiStatus;
	}




	public void setImeiStatus(String imeiStatus) {
		this.imeiStatus = imeiStatus;
	}




	public String getStatusRemarks() {
		return statusRemarks;
	}




	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GdceData [id=");
		builder.append(id);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", importerId=");
		builder.append(importerId);
		builder.append(", importerName=");
		builder.append(importerName);
		builder.append(", serialNumber=");
		builder.append(serialNumber);
		builder.append(", registrationDate=");
		builder.append(registrationDate);
		builder.append(", dateOfActualImport=");
		builder.append(dateOfActualImport);
		builder.append(", isUsed=");
		builder.append(isUsed);
		builder.append(", isCustomTaxPaid=");
		builder.append(isCustomTaxPaid);
		builder.append(", actualImei=");
		builder.append(actualImei);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", source=");
		builder.append(source);
		builder.append(", requestId=");
		builder.append(requestId);
		builder.append(", imeiStatus=");
		builder.append(imeiStatus);
		builder.append(", statusRemarks=");
		builder.append(statusRemarks);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append("]");
		return builder.toString();
	}




	
}
