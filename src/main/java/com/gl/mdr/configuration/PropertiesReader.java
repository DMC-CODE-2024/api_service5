package com.gl.mdr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesReader {

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String dialect;
	
//	@Value("${date.view.format}")
//	public String dateViewFormat;
	
	@Value("${local-ip}")
	public String localIp;
	
	@Value("#{new Integer('${serverId}')}")
	public Integer serverId;
	
	
	@Value("${NotificationAPI}")
	public String NotificationAPI;
	
//	@Value("${otp_sms_en}")
//	public String otpSms_en;
//	
//	@Value("${otp_sms_km}")
//	public String otpSms_km;
	
	@Value("${OTP_EXPIRE_TIME}")
	public long OTP_EXPIRE_TIME;
	
	//Need for TRACK LOST DEVIVE API
	@Value("${WHITE_LIST_IP}")
	public String WHITE_LIST_IP;
	
	@Value("${WHITE_LIST_IP_STATUS}")
	public boolean WHITE_LIST_IP_STATUS;

	@Value("${redmineUrl}")
	public String redmineUrl;

	@Value("${redmineFileDownloadUrl}")
	public String redmineFileDownloadUrl;

	@Value("${recoveryFilePath}")
	public String recoveryFilePath;

	//END for TRACK LOST DEVIVE API
	@Value("${eirs.alert.url}")
	private String alertURL;
}
