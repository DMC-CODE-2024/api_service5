package com.gl.mdr.feature.stolenImeiStatusCheck.model;

public class StolenViewResponse {
    private String requestId;
    private String contactNumber;
    private String deviceOwnerName;
    private String requestMode;
    private Long count;
    private String deviceOwnerAddress;
    private String deviceLostPoliceStation;
    private String transactionId;



    public StolenViewResponse(String requestId, String contactNumber, String deviceOwnerName,
                              String requestMode,String deviceOwnerAddress, String deviceLostPoliceStation,String transactionId, Long count) {
        this.requestId = requestId;
        this.contactNumber = contactNumber;
        this.deviceOwnerName = deviceOwnerName;
        this.requestMode=requestMode;
        this.deviceOwnerAddress=deviceOwnerAddress;
        this.deviceLostPoliceStation=deviceLostPoliceStation;
        this.transactionId=transactionId;
        this.count = count;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getDeviceOwnerName() {
        return deviceOwnerName;
    }

    public void setDeviceOwnerName(String deviceOwnerName) {
        this.deviceOwnerName = deviceOwnerName;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    public String getDeviceOwnerAddress() {
        return deviceOwnerAddress;
    }

    public void setDeviceOwnerAddress(String deviceOwnerAddress) {
        this.deviceOwnerAddress = deviceOwnerAddress;
    }

    public String getDeviceLostPoliceStation() {
        return deviceLostPoliceStation;
    }

    public void setDeviceLostPoliceStation(String deviceLostPoliceStation) {
        this.deviceLostPoliceStation = deviceLostPoliceStation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "StolenViewResponse{" +
                "requestId='" + requestId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", deviceOwnerName='" + deviceOwnerName + '\'' +
                ", requestMode='" + requestMode + '\'' +
                ", count=" + count +
                ", deviceOwnerAddress='" + deviceOwnerAddress + '\'' +
                ", deviceLostPoliceStation='" + deviceLostPoliceStation + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
