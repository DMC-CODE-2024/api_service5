package com.gl.mdr.model.app;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "track_lost_devices")
public class TrackLostDevices {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(name="created_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(name="modified_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedOn = LocalDateTime.now();
	
	
	@Column(name="protocol", length=155, columnDefinition="varchar(155) DEFAULT ''")
	private String protocol = "";
	
	@Column(name="imsi", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String imsi = "";
	
	@Column(name="imei", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String imei = "";
	
	@Column(name="msisdn", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String msisdn = "";
	
	@Column(name="tac", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String tac = "";
	
	@Column(name="device_type", length=55, columnDefinition="varchar(25) DEFAULT ''")
	private String deviceType = "";
	
//	@Column(name="result", length=255, columnDefinition="varchar(255) DEFAULT ''")
//	private String result = "";
	
	@Column(name="applied_list_name", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String list_type = "";
	
	@Column(name="operator", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String operator = "";
	
	@Column(name="server", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String server = "";
	
	@Column(name="session_id", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String session_id = "";
	
	@Column(name="status", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String status;
	
	@Column(name="origin_host", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String origin_host = "";
	
	@Column(name="hostname", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String hostname = "";
	
	@Column(name="time_stamp", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String time_stamp = "";
	
	@Column(name="time_taken", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String time_taken = "";
	
//	@Column(name="value", length=255, columnDefinition="varchar(255) DEFAULT ''")
//	private String value = "";
	
	@Column(name="reason_code", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String reason_code = "";
	
	@Column(name="request_id", length=255, columnDefinition="varchar(20) DEFAULT ''")
	private String request_id = "";
	
	
	@Column(name="request_type", length=20, columnDefinition="varchar(20) DEFAULT ''")
	private String requestType = "";
	
	@Transient
	private String interpretation;

	public TrackLostDevices() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

//	public String getResult() {
//		return result;
//	}
//
//	public void setResult(String result) {
//		this.result = result;
//	}

	public String getList_type() {
		return list_type;
	}

	public void setList_type(String list_type) {
		this.list_type = list_type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigin_host() {
		return origin_host;
	}

	public void setOrigin_host(String origin_host) {
		this.origin_host = origin_host;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getTime_taken() {
		return time_taken;
	}

	public void setTime_taken(String time_taken) {
		this.time_taken = time_taken;
	}

//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

		
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	@Override
	public String toString() {
		return "TrackLostDevices [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", protocol="
				+ protocol + ", imsi=" + imsi + ", imei=" + imei + ", msisdn=" + msisdn + ", tac=" + tac
				+ ", deviceType=" + deviceType + ", list_type=" + list_type + ", operator=" + operator + ", server="
				+ server + ", session_id=" + session_id + ", status=" + status + ", origin_host=" + origin_host
				+ ", hostname=" + hostname + ", time_stamp=" + time_stamp + ", time_taken=" + time_taken
				+ ", reason_code=" + reason_code + ", request_id=" + request_id + ", request_type=" + requestType
				+ ", interpretation=" + interpretation + "]";
	}

	
	
}
