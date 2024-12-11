package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exception_list_his")
public class ExceptionListHis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_on",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn;

    @Column(name = "imei")
    private String imei;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "operation")
    private Integer operation;

    @Transient
    private String tableName= Tags.exception_list_his;

    @Column(name = "source")
    private String source;

    @Transient
    private String operationInterp;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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

    public String getOperationInterp() {
        return operationInterp;
    }

    public void setOperationInterp(String operationInterp) {
        this.operationInterp = operationInterp;
    }

    @Override
    public String toString() {
        return "ExceptionListHis{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", operation=" + operation +
                ", tableName='" + tableName + '\'' +
                ", source='" + source + '\'' +
                ", operationInterp='" + operationInterp + '\'' +
                '}';
    }
}
