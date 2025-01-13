package com.gl.mdr.model.generic;

public class GenricResponse {

	private int errorCode;
	private String message;
	private String txnId;
	private Object data;
	private String tag;
	private String statusCode;
	private String requestID;

	public GenricResponse(int errorCode, String message, String txnId) {
		this.errorCode = errorCode;
		this.message = message;
		this.txnId = txnId;
	}
	
	public GenricResponse(int errorCode, String message, String txnId, String tag ) {
		this.errorCode = errorCode;
		this.message = message;
		this.txnId = txnId;
		this.tag = tag;
	}
	
	public GenricResponse(int errorCode, String message, String txnId, Object data) {
		this.errorCode = errorCode;
		this.message = message;
		this.txnId = txnId;
		this.data = data;
	}

	public GenricResponse() {
		// TODO Auto-generated constructor stub
	}

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	@Override
	public String toString() {
		return "GenricResponse{" +
				"errorCode=" + errorCode +
				", message='" + message + '\'' +
				", txnId='" + txnId + '\'' +
				", data=" + data +
				", tag='" + tag + '\'' +
				", statusCode='" + statusCode + '\'' +
				", requestID='" + requestID + '\'' +
				'}';
	}
}
