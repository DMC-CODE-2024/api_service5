package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.mdr.bulk.imei.entity.AuditTrailModel;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "search_imei_detail_by_police")
public class SearchImeiDetailByPolice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "imei", nullable = false)
    private String imei;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "lost_date_time")
    private LocalDateTime lostDateTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "device_owner_name")
    private String deviceOwnerName;

    @Column(name = "device_owner_address")
    private String deviceOwnerAddress;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "device_owner_national_id")
    private String deviceOwnerNationalId;

    @Column(name = "device_lost_police_station")
    private String deviceLostPoliceStation;

    @Column(name = "request_mode")
    private String requestMode;

    @Transient
    private String deviceLostCommune;

    @Transient
    private String fileRecordCount;

    @Transient
    Map<String,String> map = new LinkedHashMap<>();

    @Transient
    private Long matchedIMEICount;

    public Long getMatchedIMEICount() {
        return matchedIMEICount;
    }

    public void setMatchedIMEICount(Long matchedIMEICount) {
        this.matchedIMEICount = matchedIMEICount;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getDeviceLostCommune() {
        return deviceLostCommune;
    }

    public void setDeviceLostCommune(String deviceLostCommune) {
        this.deviceLostCommune = deviceLostCommune;
    }

    public String getFileRecordCount() {
        return fileRecordCount;
    }

    public void setFileRecordCount(String fileRecordCount) {
        this.fileRecordCount = fileRecordCount;
    }

    @Transient
    @JsonProperty(value = "auditTrailModel", access = JsonProperty.Access.WRITE_ONLY)
    private AuditTrailModel auditTrailModel;

    public AuditTrailModel getAuditTrailModel() {
        return auditTrailModel;
    }

    public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
        this.auditTrailModel = auditTrailModel;
    }

    public String getDeviceLostPoliceStation() {
        return deviceLostPoliceStation;
    }

    public void setDeviceLostPoliceStation(String deviceLostPoliceStation) {
        this.deviceLostPoliceStation = deviceLostPoliceStation;
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

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public LocalDateTime getLostDateTime() {
        return lostDateTime;
    }

    public void setLostDateTime(LocalDateTime lostDateTime) {
        this.lostDateTime = lostDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDeviceOwnerName() {
        return deviceOwnerName;
    }

    public void setDeviceOwnerName(String deviceOwnerName) {
        this.deviceOwnerName = deviceOwnerName;
    }

    public String getDeviceOwnerAddress() {
        return deviceOwnerAddress;
    }

    public void setDeviceOwnerAddress(String deviceOwnerAddress) {
        this.deviceOwnerAddress = deviceOwnerAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDeviceOwnerNationalId() {
        return deviceOwnerNationalId;
    }

    public void setDeviceOwnerNationalId(String deviceOwnerNationalId) {
        this.deviceOwnerNationalId = deviceOwnerNationalId;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    @Override
    public String toString() {
        return "SearchImeiDetailByPolice{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", imei='" + imei + '\'' +
                ", lostDateTime=" + lostDateTime +
                ", createdBy='" + createdBy + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", deviceOwnerName='" + deviceOwnerName + '\'' +
                ", deviceOwnerAddress='" + deviceOwnerAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", deviceOwnerNationalId='" + deviceOwnerNationalId + '\'' +
                ", deviceLostPoliceStation='" + deviceLostPoliceStation + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", deviceLostCommune='" + deviceLostCommune + '\'' +
                ", fileRecordCount='" + fileRecordCount + '\'' +
                ", map=" + map +
                ", matchedIMEICount=" + matchedIMEICount +
                ", auditTrailModel=" + auditTrailModel +
                '}';
    }
}
