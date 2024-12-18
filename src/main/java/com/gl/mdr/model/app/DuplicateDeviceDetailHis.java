package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicate_device_detail_his")
public class DuplicateDeviceDetailHis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "imei")
    private String imei;

    @Column(name = "actual_imei")
    private String actualImei;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "edr_time")
    private LocalDateTime edrTime;

    @Column(name = "operator")
    private String operator;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remarks;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "document_type1")
    private String documentType1;

    @Column(name = "document_type2")
    private String documentType2;

    @Column(name = "document_type3")
    private String documentType3;

    @Column(name = "document_type4")
    private String documentType4;

    @Column(name = "document_path1")
    private String documentPath1;

    @Column(name = "document_path2")
    private String documentPath2;

    @Column(name = "document_path3")
    private String documentPath3;

    @Column(name = "document_path4")
    private String documentPath4;

    @Column(name = "reminder_status")
    private Integer reminderStatus;

    @Column(name = "success_count")
    private Integer successCount;

    @Column(name = "fail_count")
    private Integer failCount;

    @Column(name = "redmine_tkt_id")
    private String redmineTktId;

    @Column(name = "operation")
    private Integer operation;

    @Column(name = "source")
    private String source;

    @Transient
    private String tableName= Tags.duplicate_device_detail_his;

    @Transient
    private String operationInterp;

    public String getOperationInterp() {
        return operationInterp;
    }

    public void setOperationInterp(String operationInterp) {
        this.operationInterp = operationInterp;
    }

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

    public String getActualImei() {
        return actualImei;
    }

    public void setActualImei(String actualImei) {
        this.actualImei = actualImei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getEdrTime() {
        return edrTime;
    }

    public void setEdrTime(LocalDateTime edrTime) {
        this.edrTime = edrTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDocumentType1() {
        return documentType1;
    }

    public void setDocumentType1(String documentType1) {
        this.documentType1 = documentType1;
    }

    public String getDocumentType2() {
        return documentType2;
    }

    public void setDocumentType2(String documentType2) {
        this.documentType2 = documentType2;
    }

    public String getDocumentType3() {
        return documentType3;
    }

    public void setDocumentType3(String documentType3) {
        this.documentType3 = documentType3;
    }

    public String getDocumentType4() {
        return documentType4;
    }

    public void setDocumentType4(String documentType4) {
        this.documentType4 = documentType4;
    }

    public String getDocumentPath1() {
        return documentPath1;
    }

    public void setDocumentPath1(String documentPath1) {
        this.documentPath1 = documentPath1;
    }

    public String getDocumentPath2() {
        return documentPath2;
    }

    public void setDocumentPath2(String documentPath2) {
        this.documentPath2 = documentPath2;
    }

    public String getDocumentPath3() {
        return documentPath3;
    }

    public void setDocumentPath3(String documentPath3) {
        this.documentPath3 = documentPath3;
    }

    public String getDocumentPath4() {
        return documentPath4;
    }

    public void setDocumentPath4(String documentPath4) {
        this.documentPath4 = documentPath4;
    }

    public Integer getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(Integer reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public String getRedmineTktId() {
        return redmineTktId;
    }

    public void setRedmineTktId(String redmineTktId) {
        this.redmineTktId = redmineTktId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "DuplicateDeviceDetailHis{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", imei='" + imei + '\'' +
                ", actualImei='" + actualImei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", fileName='" + fileName + '\'' +
                ", edrTime=" + edrTime +
                ", operator='" + operator + '\'' +
                ", expiryDate=" + expiryDate +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", documentType1='" + documentType1 + '\'' +
                ", documentType2='" + documentType2 + '\'' +
                ", documentType3='" + documentType3 + '\'' +
                ", documentType4='" + documentType4 + '\'' +
                ", documentPath1='" + documentPath1 + '\'' +
                ", documentPath2='" + documentPath2 + '\'' +
                ", documentPath3='" + documentPath3 + '\'' +
                ", documentPath4='" + documentPath4 + '\'' +
                ", reminderStatus=" + reminderStatus +
                ", successCount=" + successCount +
                ", failCount=" + failCount +
                ", redmineTktId='" + redmineTktId + '\'' +
                ", operation=" + operation +
                ", source='" + source + '\'' +
                ", tableName='" + tableName + '\'' +
                ", operationInterp='" + operationInterp + '\'' +
                '}';
    }
}
