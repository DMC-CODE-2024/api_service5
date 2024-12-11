package com.gl.mdr.model.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "mdr_attached_file_info")
public class AttachedFileInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ColumnDefault(value="''")
	private String fileName = "";
	
	@Column(name="mdr_id")
	private Integer mdrId;
	
	@ColumnDefault(value="''")
	private String docType = "";
	
	@Transient
	private String url;
	
	@Transient
	private String filePath;

	public Integer getId() {
		return id;
	}

	public Integer getMdrId() {
		return mdrId;
	}

	public void setMdrId(Integer mdrId) {
		this.mdrId = mdrId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "AttachedFileInfo [id=" + id + ", fileName=" + fileName + ", mdrId=" + mdrId + ", docType=" + docType
				+ ", url=" + url + ", filePath=" + filePath + "]";
	}

}