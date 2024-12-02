package com.gl.mdr.feature.stolenImeiStatusCheck.model;

public class ResponseModel {
    private int statusCode;
    private Object data;

    public ResponseModel(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseModel() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public ResponseModel setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseModel setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseModel{");
        sb.append("statusCode=").append(statusCode);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
