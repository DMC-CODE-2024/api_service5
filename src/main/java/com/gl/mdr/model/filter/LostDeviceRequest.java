package com.gl.mdr.model.filter;

public class LostDeviceRequest {
		public String imsi;//imsi
	    public String imei;//imei
	    public String msisdn;//msisdn
	    public String status;
	    //public String result;//action
	    public String hostname;
	    public String originHost;
	    public String server;
	    public String sessionId;
	    public String timeStamp;
	    public String timeTaken;
	    //public String value;
	    public String tac;//tac
	    public String deviceType;//device_type
	    public String appliedListName;//list_type
	    public Integer reasonCode;//reason_code
	    public String protocol;//protocol
//	    public String requestId="";
//	    public String requestType="";
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getHostname() {
			return hostname;
		}
		public void setHostname(String hostname) {
			this.hostname = hostname;
		}
		public String getOriginHost() {
			return originHost;
		}
		public void setOriginHost(String originHost) {
			this.originHost = originHost;
		}
		public String getServer() {
			return server;
		}
		public void setServer(String server) {
			this.server = server;
		}
		public String getSessionId() {
			return sessionId;
		}
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		
		public String getTimeTaken() {
			return timeTaken;
		}
		public void setTimeTaken(String timeTaken) {
			this.timeTaken = timeTaken;
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
		public String getAppliedListName() {
			return appliedListName;
		}
		public void setAppliedListName(String appliedListName) {
			this.appliedListName = appliedListName;
		}
		public Integer getReasonCode() {
			return reasonCode;
		}
		public void setReasonCode(Integer reasonCode) {
			this.reasonCode = reasonCode;
		}
		public String getProtocol() {
			return protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		@Override
		public String toString() {
			return "LostDeviceRequest [imsi=" + imsi + ", imei=" + imei + ", msisdn=" + msisdn + ", status=" + status
					+ ", hostname=" + hostname + ", originHost=" + originHost + ", server=" + server + ", sessionId="
					+ sessionId + ", timeStamp=" + timeStamp + ", timeTaken=" + timeTaken + ", tac=" + tac
					+ ", deviceType=" + deviceType + ", appliedListName=" + appliedListName + ", reasonCode="
					+ reasonCode + ", protocol=" + protocol + "]";
		}
	    
	    
}


