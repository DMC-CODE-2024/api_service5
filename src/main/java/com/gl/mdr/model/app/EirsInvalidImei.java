package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "eirs_invalid_imei")
public class EirsInvalidImei {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "actual_imei", unique = true)
	    private String actualImei;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;
	    
	    @Column(name = "imei")
	    private String imei;
	    
	    @Transient
	    private String tableName=Tags.eirs_invalid_imei;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getActualImei() {
			return actualImei;
		}

		public void setActualImei(String actualImei) {
			this.actualImei = actualImei;
		}

		public LocalDateTime getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
		}

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("EirsInvalidImei [id=");
			builder.append(id);
			builder.append(", actualImei=");
			builder.append(actualImei);
			builder.append(", createdOn=");
			builder.append(createdOn);
			builder.append(", imei=");
			builder.append(imei);
			builder.append(", tableName=");
			builder.append(tableName);
			builder.append("]");
			return builder.toString();
		}
	    
	    
}
