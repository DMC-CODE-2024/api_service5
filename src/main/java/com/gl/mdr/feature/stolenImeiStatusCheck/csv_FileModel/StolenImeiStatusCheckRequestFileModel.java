package com.gl.mdr.feature.stolenImeiStatusCheck.csv_FileModel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class StolenImeiStatusCheckRequestFileModel {
    @CsvBindByName(column = "Created Date")
    @CsvBindByPosition(position = 0)
    private String createdOn;

    @CsvBindByName(column = "Updated Date")
    @CsvBindByPosition(position = 1)
    private String modifiedOn;

    @CsvBindByName(column = "Transaction ID")
    @CsvBindByPosition(position = 2)
    private String transactionId;

    @CsvBindByName(column = "Request Mode")
    @CsvBindByPosition(position = 3)
    private String requestMode;


    @CsvBindByName(column = "IMEI")
    @CsvBindByPosition(position = 4)
    private String imei1;

    @CsvBindByName(column = "File Name")
    @CsvBindByPosition(position = 5)
    private String fileName;

    @CsvBindByName(column = "Record Count")
    @CsvBindByPosition(position = 6)
    private Integer fileRecordCount;

    @CsvBindByName(column = "IMEI Found Count")
    @CsvBindByPosition(position = 7)
    private Integer countFoundInLost;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 8)
    private String status;

    @CsvBindByName(column = "Remark")
    @CsvBindByPosition(position = 9)
    private String remark;

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getImei1(String imei1) {
        return this.imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
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

    @Override
    public String toString() {
        return "StolenImeiStatusCheckRequestFileModel{" +
                "createdOn='" + createdOn + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                ", imei1='" + imei1 + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileRecordCount=" + fileRecordCount +
                ", countFoundInLost=" + countFoundInLost +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
