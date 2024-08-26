package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.TrcLocalManufacturedDeviceData;

@Repository
public interface TrcLocalManufacturedDeviceDataRepo extends 
JpaRepository<TrcLocalManufacturedDeviceData, Long>, JpaSpecificationExecutor<TrcLocalManufacturedDeviceData>{
	public List<TrcLocalManufacturedDeviceData> findByImei(String imei);

	public List<TrcLocalManufacturedDeviceData> findTop1ByImei(String imei);
}
