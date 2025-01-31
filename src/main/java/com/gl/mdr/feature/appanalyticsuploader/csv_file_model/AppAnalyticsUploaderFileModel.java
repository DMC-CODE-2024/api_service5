package com.gl.mdr.feature.appanalyticsuploader.csv_file_model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDateTime;

public class AppAnalyticsUploaderFileModel {
    @CsvBindByName(column = "Id")
    @CsvBindByPosition(position = 0)
    private long id;
    @CsvBindByName(column = "Uploaded On")
    @CsvBindByPosition(position = 1)
    private LocalDateTime uploadedOn;
    @CsvBindByName(column = "OS Type")
    @CsvBindByPosition(position = 2)
    private String osType;

    @CsvBindByName(column = "Report Type")
    @CsvBindByPosition(position = 3)
    private String reportType;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 4)
    private String status;

    @CsvBindByName(column = "Transaction Id")
    @CsvBindByPosition(position = 5)
    private String transactionId;

    @CsvBindByName(column = "Insert Count")
    @CsvBindByPosition(position = 6)
    private int insertCount;

    @CsvBindByName(column = "Source File Name")
    @CsvBindByPosition(position = 7)
    private String sourceFileName;

    @CsvBindByName(column = "reason")
    @CsvBindByPosition(position = 8)
    private String reason;

    @CsvBindByName(column = "Uploaded By")
    @CsvBindByPosition(position = 9)
    private String uploadedBy;

    public long getId() {
        return id;
    }

    public AppAnalyticsUploaderFileModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public AppAnalyticsUploaderFileModel setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public LocalDateTime getUploadedOn() {
        return uploadedOn;
    }

    public AppAnalyticsUploaderFileModel setUploadedOn(LocalDateTime uploadedOn) {
        this.uploadedOn = uploadedOn;
        return this;
    }

    public String getOsType() {
        return osType;
    }

    public AppAnalyticsUploaderFileModel setOsType(String osType) {
        this.osType = osType;
        return this;
    }

    public String getReportType() {
        return reportType;
    }

    public AppAnalyticsUploaderFileModel setReportType(String reportType) {
        this.reportType = reportType;
        return this;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public AppAnalyticsUploaderFileModel setInsertCount(int insertCount) {
        this.insertCount = insertCount;
        return this;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public AppAnalyticsUploaderFileModel setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AppAnalyticsUploaderFileModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public AppAnalyticsUploaderFileModel setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public AppAnalyticsUploaderFileModel setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
