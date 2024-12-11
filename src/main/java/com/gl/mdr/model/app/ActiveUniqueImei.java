package com.gl.mdr.model.app;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "active_unique_imei")
public class ActiveUniqueImei {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;

	    @Column(name = "created_on")
	    private Timestamp createdOn;

		@Column(name = "msisdn")
	    private String msisdn;

		@Column(name = "imsi")
	    private String imsi;

		@Column(name = "imei")
	    private String imei;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return "ActiveUniqueImei{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", msisdn='" + msisdn + '\'' +
				", imsi='" + imsi + '\'' +
				", imei='" + imei + '\'' +
				'}';
	}
}
