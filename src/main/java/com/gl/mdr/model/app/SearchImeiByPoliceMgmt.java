package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.mdr.bulk.imei.entity.AuditTrailModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_imei_by_police_mgmt")
public class SearchImeiByPoliceMgmt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "imei1", length = 20)
    private String imei1;

    @Column(name = "imei2", length = 20)
    private String imei2;

    @Column(name = "imei3", length = 20)
    private String imei3;

    @Column(name = "imei4", length = 20)
    private String imei4;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "remark", length = 250)
    private String remark;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "transaction_id", length = 20, unique = true)
    private String transactionId;

    @Column(name = "request_mode", length = 8)
    private String requestMode;

    @Column(name = "file_name", length = 50)
    private String fileName;

    @Column(name = "file_record_count")
    private Integer fileRecordCount;

    @Column(name = "count_found_in_lost")
    private Integer countFoundInLost;

    @Transient
    @JsonProperty(value = "auditTrailModel", access = JsonProperty.Access.WRITE_ONLY)
    private AuditTrailModel auditTrailModel;

    public AuditTrailModel getAuditTrailModel() {
        return auditTrailModel;
    }

    public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
        this.auditTrailModel = auditTrailModel;
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

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImei3() {
        return imei3;
    }

    public void setImei3(String imei3) {
        this.imei3 = imei3;
    }

    public String getImei4() {
        return imei4;
    }

    public void setImei4(String imei4) {
        this.imei4 = imei4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileRecordCount() {
        return fileRecordCount;
    }

    public void setFileRecordCount(Integer fileRecordCount) {
        this.fileRecordCount = fileRecordCount;
    }

    public Integer getCountFoundInLost() {
        return countFoundInLost;
    }

    public void setCountFoundInLost(Integer countFoundInLost) {
        this.countFoundInLost = countFoundInLost;
    }

    @Override
    public String toString() {
        return "SearchImeiByPoliceMgmt{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", imei1='" + imei1 + '\'' +
                ", imei2='" + imei2 + '\'' +
                ", imei3='" + imei3 + '\'' +
                ", imei4='" + imei4 + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileRecordCount=" + fileRecordCount +
                ", countFoundInLost=" + countFoundInLost +
                ", auditTrailModel=" + auditTrailModel +
                '}';
    }
}
