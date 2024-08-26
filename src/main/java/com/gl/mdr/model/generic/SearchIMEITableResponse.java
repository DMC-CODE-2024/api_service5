package com.gl.mdr.model.generic;

import java.util.ArrayList;
import java.util.List;

public class SearchIMEITableResponse {
	
	List<?> tableResponse = new ArrayList<>();
	List<?> historyTableResponse = new ArrayList<>();
	public List<?> getTableResponse() {
		return tableResponse;
	}
	public void setTableResponse(List<?> tableResponse) {
		this.tableResponse = tableResponse;
	}
	public List<?> getHistoryTableResponse() {
		return historyTableResponse;
	}
	public void setHistoryTableResponse(List<?> historyTableResponse) {
		this.historyTableResponse = historyTableResponse;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchIMEITableResponse [tableResponse=");
		builder.append(tableResponse);
		builder.append(", historyTableResponse=");
		builder.append(historyTableResponse);
		builder.append("]");
		return builder.toString();
	}
	
	
}
