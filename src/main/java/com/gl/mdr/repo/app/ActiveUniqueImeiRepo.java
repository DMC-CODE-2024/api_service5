package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.ActiveUniqueImei;

@Repository
public interface ActiveUniqueImeiRepo extends 
JpaRepository<ActiveUniqueImei, Long>, JpaSpecificationExecutor<ActiveUniqueImei>{
	public ActiveUniqueImei findByImei(String imei);

	public ActiveUniqueImei findByImeiAndMsisdn(String imei, String msisdn);
}
