package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "imei_pair_detail")
public class ImeiPairDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "imei")
    private String imei;

    @Column(name = "imsi")
    private String imsi;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn;

	@Column(name = "pair_mode")
    private String pairMode;

    @Transient
    private String tableName=Tags.imei_pair_detail;

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getPairMode() {
		return pairMode;
	}

	public void setPairMode(String pairMode) {
		this.pairMode = pairMode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "ImeiPairDetail{" +
				"id=" + id +
				", msisdn='" + msisdn + '\'' +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", createdOn=" + createdOn +
				", pairMode='" + pairMode + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
