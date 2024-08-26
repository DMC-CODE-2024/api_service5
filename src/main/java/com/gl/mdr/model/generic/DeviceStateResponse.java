package com.gl.mdr.model.generic;

import java.util.List;

public class DeviceStateResponse {
	 private List<List<SearchIMEIResponse>> result;

	    public DeviceStateResponse(List<List<SearchIMEIResponse>> result) {
	        this.result = result;
	    }

		public List<List<SearchIMEIResponse>> getResult() {
			return result;
		}

		public void setResult(List<List<SearchIMEIResponse>> result) {
			this.result = result;
		}
	    
	    
}
