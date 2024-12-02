package com.gl.mdr.model.app;

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
@Table(name = "imei_pair_detail_his")
public class ImeiPairDetailHistory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id")
	    private Integer id;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;

	    @Column(name = "imei")
	    private String imei;

	    @Column(name = "imsi")
	    private String imsi;

	    @Column(name = "msisdn")
	    private String msisdn;

	    @Column(name = "pair_mode")
	    private String pairMode;

		@Column(name = "action")
	    private String action;

	    @Transient
	    private String tableName=Tags.imei_pair_detail_his;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
		return "ImeiPairDetailHistory{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", imsi='" + imsi + '\'' +
				", msisdn='" + msisdn + '\'' +
				", pairMode='" + pairMode + '\'' +
				", action='" + action + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
