package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.mdr.model.app.EirsInvalidImei;

public interface EirsInvalidImeiRepo extends JpaRepository<EirsInvalidImei, Long>, JpaSpecificationExecutor<EirsInvalidImei> {
	public List<EirsInvalidImei> findByImei(String imei);
	public List<EirsInvalidImei> findTop1ByImei(String imei);
	
}