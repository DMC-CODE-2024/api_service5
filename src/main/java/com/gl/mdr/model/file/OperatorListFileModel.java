package com.gl.mdr.model.file;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class OperatorListFileModel {
	
	@CsvBindByName(column = "Date")
	@CsvBindByPosition(position = 0)
	private LocalDateTime created_on;
	
	@CsvBindByName(column = "File Name")
	@CsvBindByPosition(position = 1)
	private String file_name;
	
	@CsvBindByName(column = "File Type")
	@CsvBindByPosition(position = 2)
	private String file_type;
	
	@CsvBindByName(column = "File Path")
	@CsvBindByPosition(position = 3)
	private String file_path;
	
	@CsvBindByName(column = "Operator")
	@CsvBindByPosition(position = 4)
	private String operator_name;

	public LocalDateTime getCreated_on() {
		return created_on;
	}

	public void setCreated_on(LocalDateTime created_on) {
		this.created_on = created_on;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	@Override
	public String toString() {
		return "OperatorListFileModel [created_on=" + created_on + ", file_name=" + file_name + ", file_type="
				+ file_type + ", file_path=" + file_path + ", operator_name=" + operator_name + "]";
	}
	

	
}
