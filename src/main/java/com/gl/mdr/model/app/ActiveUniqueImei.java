package com.gl.mdr.model.app;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "active_unique_imei")
public class ActiveUniqueImei {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;

	    @Column(name = "created_on")
	    private Timestamp createdOn;

	    @Column(name = "modified_on")
	    private Timestamp modifiedOn;

	    @Column(name = "foregin_rule")
	    private String foreginRule;

	    @Column(name = "tac")
	    private String tac;

	    @Column(name = "msisdn")
	    private String msisdn;

	    @Column(name = "failed_rule_id")
	    private Integer failedRuleId;

	    @Column(name = "failed_rule_name")
	    private String failedRuleName;

	    @Column(name = "imsi")
	    private String imsi;

	    @Column(name = "mobile_operator")
	    private String mobileOperator;

	    @Column(name = "create_filename")
	    private String createFilename;

	    @Column(name = "update_filename")
	    private String updateFilename;

	    @Column(name = "updated_on")
	    private Timestamp updatedOn;

	    @Column(name = "system_type")
	    private String systemType;

	    @Column(name = "action")
	    private String action;

	    @Column(name = "period")
	    private String period;

	    @Column(name = "failed_rule_date")
	    private Timestamp failedRuleDate;

	    @Column(name = "mobile_operator_id")
	    private Integer mobileOperatorId;

	    @Column(name = "tax_paid")
	    private Integer taxPaid;

	    @Column(name = "feature_name")
	    private String featureName;

	    @Column(name = "record_time")
	    private Timestamp recordTime;

	    @Column(name = "actual_imei")
	    private String actualImei;

	    @Column(name = "record_type")
	    private String recordType;

	    @Column(name = "imei")
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

	    @Column(name = "actual_operator")
	    private String actualOperator;

	    @Column(name = "test_imei")
	    private String testImei;

	    @Column(name = "is_used")
	    private String isUsed;

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

		public Timestamp getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(Timestamp modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

		public String getForeginRule() {
			return foreginRule;
		}

		public void setForeginRule(String foreginRule) {
			this.foreginRule = foreginRule;
		}

		public String getTac() {
			return tac;
		}

		public void setTac(String tac) {
			this.tac = tac;
		}

		public String getMsisdn() {
			return msisdn;
		}

		public void setMsisdn(String msisdn) {
			this.msisdn = msisdn;
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

		public String getImsi() {
			return imsi;
		}

		public void setImsi(String imsi) {
			this.imsi = imsi;
		}

		public String getMobileOperator() {
			return mobileOperator;
		}

		public void setMobileOperator(String mobileOperator) {
			this.mobileOperator = mobileOperator;
		}

		public String getCreateFilename() {
			return createFilename;
		}

		public void setCreateFilename(String createFilename) {
			this.createFilename = createFilename;
		}

		public String getUpdateFilename() {
			return updateFilename;
		}

		public void setUpdateFilename(String updateFilename) {
			this.updateFilename = updateFilename;
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

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public Timestamp getFailedRuleDate() {
			return failedRuleDate;
		}

		public void setFailedRuleDate(Timestamp failedRuleDate) {
			this.failedRuleDate = failedRuleDate;
		}

		public Integer getMobileOperatorId() {
			return mobileOperatorId;
		}

		public void setMobileOperatorId(Integer mobileOperatorId) {
			this.mobileOperatorId = mobileOperatorId;
		}

		public Integer getTaxPaid() {
			return taxPaid;
		}

		public void setTaxPaid(Integer taxPaid) {
			this.taxPaid = taxPaid;
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

		public String getActualOperator() {
			return actualOperator;
		}

		public void setActualOperator(String actualOperator) {
			this.actualOperator = actualOperator;
		}

		public String getTestImei() {
			return testImei;
		}

		public void setTestImei(String testImei) {
			this.testImei = testImei;
		}

		public String getIsUsed() {
			return isUsed;
		}

		public void setIsUsed(String isUsed) {
			this.isUsed = isUsed;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ActiveUniqueImei [id=");
			builder.append(id);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", modifiedOn=");
			builder.append(modifiedOn);
			builder.append(", foreginRule=");
			builder.append(foreginRule);
			builder.append(", tac=");
			builder.append(tac);
			builder.append(", msisdn=");
			builder.append(msisdn);
			builder.append(", failedRuleId=");
			builder.append(failedRuleId);
			builder.append(", failedRuleName=");
			builder.append(failedRuleName);
			builder.append(", imsi=");
			builder.append(imsi);
			builder.append(", mobileOperator=");
			builder.append(mobileOperator);
			builder.append(", createFilename=");
			builder.append(createFilename);
			builder.append(", updateFilename=");
			builder.append(updateFilename);
			builder.append(", updatedOn=");
			builder.append(updatedOn);
			builder.append(", systemType=");
			builder.append(systemType);
			builder.append(", action=");
			builder.append(action);
			builder.append(", period=");
			builder.append(period);
			builder.append(", failedRuleDate=");
			builder.append(failedRuleDate);
			builder.append(", mobileOperatorId=");
			builder.append(mobileOperatorId);
			builder.append(", taxPaid=");
			builder.append(taxPaid);
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
			builder.append(", actualOperator=");
			builder.append(actualOperator);
			builder.append(", testImei=");
			builder.append(testImei);
			builder.append(", isUsed=");
			builder.append(isUsed);
			builder.append("]");
			return builder.toString();
		}
	    
	    
}
