package com.gl.mdr.model.app;

import java.sql.Timestamp;
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
@Table(name = "national_whitelist")
public class NationalWhiteList {
	
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "national_whitelist_id")
	    private Long nationalWhitelistId;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "modified_on")
	    private LocalDateTime modifiedOn;

	    @Column(name = "foreign_rule")
	    private String foreignRule;

	    @Column(name = "mobile_operator")
	    private String mobileOperator;

	    @Column(name = "period")
	    private String period;

	    @Column(name = "tax_paid")
	    private String taxPaid;

	    @Column(name = "created_filename")
	    private String createdFilename;

	    @Column(name = "updated_filename")
	    private String updatedFilename;

	    @Column(name = "updated_on")
	    private Timestamp updatedOn;

	    @Column(name = "system_type")
	    private String systemType;

	    @Column(name = "failed_rule_id")
	    private Integer failedRuleId;

	    @Column(name = "failed_rule_name")
	    private String failedRuleName;

	    @Column(name = "validity_flag")
	    private Integer validityFlag;

	    @Column(name = "tac")
	    private String tac;

	    @Column(name = "action")
	    private String action;

	    @Column(name = "failed_rule_date")
	    private Timestamp failedRuleDate;

	    @Column(name = "feature_name")
	    private String featureName;

	    @Column(name = "record_time")
	    private Timestamp recordTime;

	    @Column(name = "actual_imei")
	    private String actualImei;

	    @Column(name = "record_type")
	    private String recordType;

	    @Column(name = "imei", unique = true)
	    private String imei;

	    @Column(name = "raw_cdr_file_name")
	    private String rawCdrFileName;

	    @Column(name = "imei_arrival_time")
	    private Timestamp imeiArrivalTime;

	    @Column(name = "source")
	    private String source;

	    @Column(name = "update_raw_cdr_file_name")
	    private String updateRawCdrFileName;

	    @Column(name = "update_imei_arrival_time")
	    private Timestamp updateImeiArrivalTime;

	    @Column(name = "update_source")
	    private String updateSource;

	    @Column(name = "server_origin")
	    private String serverOrigin;

	    @Column(name = "list_type")
	    private String listType;

	    @Column(name = "type_of_entry")
	    private String typeOfEntry;

	    @Column(name = "national_white_list_created_date")
	    private Timestamp nationalWhiteListCreatedDate;

	    @Column(name = "reason_for_invalid_imei")
	    private String reasonForInvalidImei;

	    @Column(name = "imsi")
	    private String imsi;

	    @Column(name = "msisdn")
	    private String msisdn;

	    @Column(name = "created_on_date")
	    private Timestamp createdOnDate;

	    @Column(name = "device_type")
	    private String deviceType;

	    @Column(name = "actual_operator")
	    private String actualOperator;

	    @Column(name = "is_test_imei")
	    private String isTestImei;

	    @Column(name = "is_used_device_imei")
	    private String isUsedDeviceImei;

	    @Column(name = "gdce_imei_status")
	    private Integer gdceImeiStatus;

	    @Column(name = "gdce_modified_time")
	    private Timestamp gdceModifiedTime;

	    @Column(name = "trc_imei_status")
	    private Integer trcImeiStatus;

	    @Column(name = "trc_modified_time")
	    private Timestamp trcModifiedTime;
	    
	    @Transient
	    private String tableName=Tags.national_whitelist;
	    
	    
		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
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

		public LocalDateTime getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(LocalDateTime modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		public String getForeignRule() {
			return foreignRule;
		}

		public void setForeignRule(String foreignRule) {
			this.foreignRule = foreignRule;
		}

		public String getMobileOperator() {
			return mobileOperator;
		}

		public void setMobileOperator(String mobileOperator) {
			this.mobileOperator = mobileOperator;
		}

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public String getTaxPaid() {
			return taxPaid;
		}

		public void setTaxPaid(String taxPaid) {
			this.taxPaid = taxPaid;
		}

		public String getCreatedFilename() {
			return createdFilename;
		}

		public void setCreatedFilename(String createdFilename) {
			this.createdFilename = createdFilename;
		}

		public String getUpdatedFilename() {
			return updatedFilename;
		}

		public void setUpdatedFilename(String updatedFilename) {
			this.updatedFilename = updatedFilename;
		}

		public Timestamp getUpdatedOn() {
			return updatedOn;
		}

		public void setUpdatedOn(Timestamp updatedOn) {
			this.updatedOn = updatedOn;
		}

		public String getSystemType() {
			return systemType;
		}

		public void setSystemType(String systemType) {
			this.systemType = systemType;
		}

		public Integer getFailedRuleId() {
			return failedRuleId;
		}

		public void setFailedRuleId(Integer failedRuleId) {
			this.failedRuleId = failedRuleId;
		}

		public String getFailedRuleName() {
			return failedRuleName;
		}

		public void setFailedRuleName(String failedRuleName) {
			this.failedRuleName = failedRuleName;
		}

		

		

		public Integer getValidityFlag() {
			return validityFlag;
		}

		public void setValidityFlag(Integer validityFlag) {
			this.validityFlag = validityFlag;
		}

		public String getTac() {
			return tac;
		}

		public void setTac(String tac) {
			this.tac = tac;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public Timestamp getFailedRuleDate() {
			return failedRuleDate;
		}

		public void setFailedRuleDate(Timestamp failedRuleDate) {
			this.failedRuleDate = failedRuleDate;
		}

		public String getFeatureName() {
			return featureName;
		}

		public void setFeatureName(String featureName) {
			this.featureName = featureName;
		}

		public Timestamp getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(Timestamp recordTime) {
			this.recordTime = recordTime;
		}

		public String getActualImei() {
			return actualImei;
		}

		public void setActualImei(String actualImei) {
			this.actualImei = actualImei;
		}

		public String getRecordType() {
			return recordType;
		}

		public void setRecordType(String recordType) {
			this.recordType = recordType;
		}

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}

		public String getRawCdrFileName() {
			return rawCdrFileName;
		}

		public void setRawCdrFileName(String rawCdrFileName) {
			this.rawCdrFileName = rawCdrFileName;
		}

		public Timestamp getImeiArrivalTime() {
			return imeiArrivalTime;
		}

		public void setImeiArrivalTime(Timestamp imeiArrivalTime) {
			this.imeiArrivalTime = imeiArrivalTime;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getUpdateRawCdrFileName() {
			return updateRawCdrFileName;
		}

		public void setUpdateRawCdrFileName(String updateRawCdrFileName) {
			this.updateRawCdrFileName = updateRawCdrFileName;
		}

		public Timestamp getUpdateImeiArrivalTime() {
			return updateImeiArrivalTime;
		}

		public void setUpdateImeiArrivalTime(Timestamp updateImeiArrivalTime) {
			this.updateImeiArrivalTime = updateImeiArrivalTime;
		}

		public String getUpdateSource() {
			return updateSource;
		}

		public void setUpdateSource(String updateSource) {
			this.updateSource = updateSource;
		}

		public String getServerOrigin() {
			return serverOrigin;
		}

		public void setServerOrigin(String serverOrigin) {
			this.serverOrigin = serverOrigin;
		}

		public String getListType() {
			return listType;
		}

		public void setListType(String listType) {
			this.listType = listType;
		}

		public String getTypeOfEntry() {
			return typeOfEntry;
		}

		public void setTypeOfEntry(String typeOfEntry) {
			this.typeOfEntry = typeOfEntry;
		}

		public Timestamp getNationalWhiteListCreatedDate() {
			return nationalWhiteListCreatedDate;
		}

		public void setNationalWhiteListCreatedDate(Timestamp nationalWhiteListCreatedDate) {
			this.nationalWhiteListCreatedDate = nationalWhiteListCreatedDate;
		}

		public String getReasonForInvalidImei() {
			return reasonForInvalidImei;
		}

		public void setReasonForInvalidImei(String reasonForInvalidImei) {
			this.reasonForInvalidImei = reasonForInvalidImei;
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

		public Timestamp getCreatedOnDate() {
			return createdOnDate;
		}

		public void setCreatedOnDate(Timestamp createdOnDate) {
			this.createdOnDate = createdOnDate;
		}

		public String getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(String deviceType) {
			this.deviceType = deviceType;
		}

		public String getActualOperator() {
			return actualOperator;
		}

		public void setActualOperator(String actualOperator) {
			this.actualOperator = actualOperator;
		}

		public String getIsTestImei() {
			return isTestImei;
		}

		public void setIsTestImei(String isTestImei) {
			this.isTestImei = isTestImei;
		}

		public String getIsUsedDeviceImei() {
			return isUsedDeviceImei;
		}

		public void setIsUsedDeviceImei(String isUsedDeviceImei) {
			this.isUsedDeviceImei = isUsedDeviceImei;
		}

		public Integer getGdceImeiStatus() {
			return gdceImeiStatus;
		}

		public void setGdceImeiStatus(Integer gdceImeiStatus) {
			this.gdceImeiStatus = gdceImeiStatus;
		}

		public Timestamp getGdceModifiedTime() {
			return gdceModifiedTime;
		}

		public void setGdceModifiedTime(Timestamp gdceModifiedTime) {
			this.gdceModifiedTime = gdceModifiedTime;
		}

		public Integer getTrcImeiStatus() {
			return trcImeiStatus;
		}

		public void setTrcImeiStatus(Integer trcImeiStatus) {
			this.trcImeiStatus = trcImeiStatus;
		}

		public Timestamp getTrcModifiedTime() {
			return trcModifiedTime;
		}

		public void setTrcModifiedTime(Timestamp trcModifiedTime) {
			this.trcModifiedTime = trcModifiedTime;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("NationalWhiteList [nationalWhitelistId=");
			builder.append(nationalWhitelistId);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", modifiedOn=");
			builder.append(modifiedOn);
			builder.append(", foreignRule=");
			builder.append(foreignRule);
			builder.append(", mobileOperator=");
			builder.append(mobileOperator);
			builder.append(", period=");
			builder.append(period);
			builder.append(", taxPaid=");
			builder.append(taxPaid);
			builder.append(", createdFilename=");
			builder.append(createdFilename);
			builder.append(", updatedFilename=");
			builder.append(updatedFilename);
			builder.append(", updatedOn=");
			builder.append(updatedOn);
			builder.append(", systemType=");
			builder.append(systemType);
			builder.append(", failedRuleId=");
			builder.append(failedRuleId);
			builder.append(", failedRuleName=");
			builder.append(failedRuleName);
			builder.append(", validityFlag=");
			builder.append(validityFlag);
			builder.append(", tac=");
			builder.append(tac);
			builder.append(", action=");
			builder.append(action);
			builder.append(", failedRuleDate=");
			builder.append(failedRuleDate);
			builder.append(", featureName=");
			builder.append(featureName);
			builder.append(", recordTime=");
			builder.append(recordTime);
			builder.append(", actualImei=");
			builder.append(actualImei);
			builder.append(", recordType=");
			builder.append(recordType);
			builder.append(", imei=");
			builder.append(imei);
			builder.append(", rawCdrFileName=");
			builder.append(rawCdrFileName);
			builder.append(", imeiArrivalTime=");
			builder.append(imeiArrivalTime);
			builder.append(", source=");
			builder.append(source);
			builder.append(", updateRawCdrFileName=");
			builder.append(updateRawCdrFileName);
			builder.append(", updateImeiArrivalTime=");
			builder.append(updateImeiArrivalTime);
			builder.append(", updateSource=");
			builder.append(updateSource);
			builder.append(", serverOrigin=");
			builder.append(serverOrigin);
			builder.append(", listType=");
			builder.append(listType);
			builder.append(", typeOfEntry=");
			builder.append(typeOfEntry);
			builder.append(", nationalWhiteListCreatedDate=");
			builder.append(nationalWhiteListCreatedDate);
			builder.append(", reasonForInvalidImei=");
			builder.append(reasonForInvalidImei);
			builder.append(", imsi=");
			builder.append(imsi);
			builder.append(", msisdn=");
			builder.append(msisdn);
			builder.append(", createdOnDate=");
			builder.append(createdOnDate);
			builder.append(", deviceType=");
			builder.append(deviceType);
			builder.append(", actualOperator=");
			builder.append(actualOperator);
			builder.append(", isTestImei=");
			builder.append(isTestImei);
			builder.append(", isUsedDeviceImei=");
			builder.append(isUsedDeviceImei);
			builder.append(", gdceImeiStatus=");
			builder.append(gdceImeiStatus);
			builder.append(", gdceModifiedTime=");
			builder.append(gdceModifiedTime);
			builder.append(", trcImeiStatus=");
			builder.append(trcImeiStatus);
			builder.append(", trcModifiedTime=");
			builder.append(trcModifiedTime);
			builder.append(", tableName=");
			builder.append(tableName);
			builder.append("]");
			return builder.toString();
		}

		
}
