package com.gl.mdr.feature.stolenImeiStatusCheck.model;

public class StolenMatchedImeiResponse {
    private String lostDeviceImei;
    private String matchedImei;

    public StolenMatchedImeiResponse(String lostDeviceImei, String matchedImei) {
        this.lostDeviceImei = lostDeviceImei;
        this.matchedImei=matchedImei;
    }

    public String getLostDeviceImei() {
        return lostDeviceImei;
    }

    public void setLostDeviceImei(String lostDeviceImei) {
        this.lostDeviceImei = lostDeviceImei;
    }

    public String getMatchedImei() {
        return matchedImei;
    }

    public void setMatchedImei(String matchedImei) {
        this.matchedImei = matchedImei;
    }

    @Override
    public String toString() {
        return "StolenMatchedImeiResponse{" +
                "lostDeviceImei='" + lostDeviceImei + '\'' +
                ", matchedImei='" + matchedImei + '\'' +
                '}';
    }
}
