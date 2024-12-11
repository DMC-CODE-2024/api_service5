package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "list_file_mgmt")
public class ListFileManagementModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Transient
	private int statusCode;

	@Column(name="created_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;

	@Column(name="modified_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;
	
	@Column(name = "file_name") 
	private String fileName;
	
	@Column(name = "file_path") 
	private String filePath;
	
	@Column(name = "source_server") 
	private String sourceServer;
	
	@Column(name = "list_type") 
	private String listType;
	
	@Column(name = "operator_name") 
	private String operatorName;
	
	@Column(name = "file_type") 
	private String fileType;
	
	@Column(name = "file_state") 
	private int fileState;
	
	@Column(name = "record_count") 
	private int recordCount;
	
	@Column(name = "copy_status") 
	private int copyStatus;
	

	@Column(name = "destination_path") 
	private String destinationPath;
	
	@Column(name = "destination_server") 
	private String destinationServer;
	
	
	@Transient
	private String interpretation;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSourceServer() {
		return sourceServer;
	}

	public void setSourceServer(String sourceServer) {
		this.sourceServer = sourceServer;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getFileState() {
		return fileState;
	}

	public void setFileState(int fileState) {
		this.fileState = fileState;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(int copyStatus) {
		this.copyStatus = copyStatus;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public String getDestinationServer() {
		return destinationServer;
	}

	public void setDestinationServer(String destinationServer) {
		this.destinationServer = destinationServer;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	@Override
	public String toString() {
		return "ListFileManagementModel [ID=" + ID + ", statusCode=" + statusCode + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", fileName=" + fileName + ", filePath=" + filePath + ", sourceServer="
				+ sourceServer + ", listType=" + listType + ", operatorName=" + operatorName + ", fileType=" + fileType
				+ ", fileState=" + fileState + ", recordCount=" + recordCount + ", copyStatus=" + copyStatus
				+ ", destinationPath=" + destinationPath + ", destinationServer=" + destinationServer
				+ ", interpretation=" + interpretation + "]";
	}

	
}
