package com.gl.mdr.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "national_whitelist")
public class NationalWhiteList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "national_whitelist_id")
	private Long nationalWhitelistId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "validity_flag")
	private Integer validityFlag;

	@Column(name = "imei", length = 20)
	private String imei;


	@Column(name = "imsi", length = 20)
	private String imsi;

	@Column(name = "msisdn", length = 20)
	private String msisdn;

	@Column(name = "reason_for_invalid_imei", length = 50)
	private String reasonForInvalidImei;

	@Column(name = "gdce_imei_status")
	private Integer gdceImeiStatus;

	@Column(name = "trc_imei_status")
	private Integer trcImeiStatus;

	/*@Column(name = "reason", length = 50)
	private String reason;*/

	@Transient
	private String tableName= Tags.national_whitelist;

	@Transient
	private String gdceStatusInterp;

	@Transient
	private String trcImeiStatusInterp;

	@Transient
	private String validityFlagInterp;

	public String getValidityFlagInterp() {
		return validityFlagInterp;
	}

	public void setValidityFlagInterp(String validityFlagInterp) {
		this.validityFlagInterp = validityFlagInterp;
	}

	public String getGdceStatusInterp() {
		return gdceStatusInterp;
	}

	public void setGdceStatusInterp(String gdceStatusInterp) {
		this.gdceStatusInterp = gdceStatusInterp;
	}

	public String getTrcImeiStatusInterp() {
		return trcImeiStatusInterp;
	}

	public void setTrcImeiStatusInterp(String trcImeiStatusInterp) {
		this.trcImeiStatusInterp = trcImeiStatusInterp;
	}


	public Integer getValidityFlag() {
		return validityFlag;
	}

	public void setValidityFlag(Integer validityFlag) {
		this.validityFlag = validityFlag;
	}

	public Long getNationalWhitelistId() {
		return nationalWhitelistId;
	}

	public void setNationalWhitelistId(Long nationalWhitelistId) {
		this.nationalWhitelistId = nationalWhitelistId;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getReasonForInvalidImei() {
		return reasonForInvalidImei;
	}

	public void setReasonForInvalidImei(String reasonForInvalidImei) {
		this.reasonForInvalidImei = reasonForInvalidImei;
	}

	public Integer getGdceImeiStatus() {
		return gdceImeiStatus;
	}

	public void setGdceImeiStatus(Integer gdceImeiStatus) {
		this.gdceImeiStatus = gdceImeiStatus;
	}

	public Integer getTrcImeiStatus() {
		return trcImeiStatus;
	}

	public void setTrcImeiStatus(Integer trcImeiStatus) {
		this.trcImeiStatus = trcImeiStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "NationalWhiteList{" +
				"nationalWhitelistId=" + nationalWhitelistId +
				", createdOn=" + createdOn +
				", validityFlag=" + validityFlag +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", reasonForInvalidImei='" + reasonForInvalidImei + '\'' +
				", gdceImeiStatus=" + gdceImeiStatus +
				", trcImeiStatus=" + trcImeiStatus +
				", tableName='" + tableName + '\'' +
				", gdceStatusInterp='" + gdceStatusInterp + '\'' +
				", trcImeiStatusInterp='" + trcImeiStatusInterp + '\'' +
				", validityFlagInterp='" + validityFlagInterp + '\'' +
				'}';
	}
}
