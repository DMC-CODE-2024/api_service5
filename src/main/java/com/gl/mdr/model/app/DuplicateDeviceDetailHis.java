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

   /* @Column(name = "document_type1")
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
    private String documentPath4;*/

    @Column(name = "reminder_status")
    private Integer reminderStatus;

    @Column(name = "success_count")
    private Integer successCount;

    @Column(name = "fail_count")
    private Integer failCount;

    @Column(name = "redmine_tkt_id")
    private String redmineTktId;

    @Column(name = "action")
    private String action;

    @Column(name = "action_remark")
    private String actionRemark;

    @Transient
    private String tableName= Tags.duplicate_device_detail_his;

    public String getActionRemark() {
        return actionRemark;
    }

    public void setActionRemark(String actionRemark) {
        this.actionRemark = actionRemark;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
                ", reminderStatus=" + reminderStatus +
                ", successCount=" + successCount +
                ", failCount=" + failCount +
                ", redmineTktId='" + redmineTktId + '\'' +
                ", action='" + action + '\'' +
                ", actionRemark='" + actionRemark + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
