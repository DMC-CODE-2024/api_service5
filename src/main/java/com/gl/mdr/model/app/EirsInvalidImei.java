package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl.mdr.model.constants.Tags;

@Entity
@Table(name = "eirs_invalid_imei")
public class EirsInvalidImei {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;


	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	    @Column(name = "created_on")
	    private LocalDateTime createdOn;
	    
	    @Column(name = "imei")
	    private String imei;

	@Column(name = "actual_imei")
	private String actualImei;
	    
	    @Transient
	    private String tableName=Tags.eirs_invalid_imei;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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



	public String getActualImei() {
		return actualImei;
	}

	public void setActualImei(String actualImei) {
		this.actualImei = actualImei;
	}

	@Override
	public String toString() {
		return "EirsInvalidImei{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", imei='" + imei + '\'' +
				", actualImei='" + actualImei + '\'' +
				", tableName='" + tableName + '\'' +
				'}';
	}
}
