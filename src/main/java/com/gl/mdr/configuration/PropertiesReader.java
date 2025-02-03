package com.gl.mdr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesReader {

    @Value("${spring.jpa.properties.hibernate.dialect}")
    public String dialect;

    @Value("${local-ip}")
    public String localIp;

    @Value("#{new Integer('${serverId}')}")
    public Integer serverId;


    @Value("${NotificationAPI}")
    public String NotificationAPI;


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

    @Value("${stolenFeatureName}")
    public String stolenFeatureName;

    @Value("${bulkCheckIMEIFeatureName}")
    public String bulkCheckIMEIFeatureName;

    @Value("${groupId}")
    public Long groupId;

    //Dynamic feature name for AUDIT purpose.
    @Value("${sidebar.Duplicate_Management}")
    public String DuplicateManagement;

    @Value("${sidebar.Device_Management}")
    public String DeviceManagement;

    @Value("${sidebar.Dashboard_MDR}")
    public String mdrDashboard;

    @Value("${sidebar.Stolen_IMEI_Status_Check}")
    public String StolenIMEIStatusCheck;

}
