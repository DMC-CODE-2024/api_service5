package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.BlackList;

@Repository
public interface BlackListRepo extends 
JpaRepository<BlackList, Long>, JpaSpecificationExecutor<BlackList> {
	
	public List<BlackList> findByImei(String imei);

	//public BlackList findByImeiAndMsisdn(String imei, String msisdn);
	
	public List<BlackList> findByImeiAndImsi(String imei, String imsi);

	public List<BlackList> findTop1ByImei(String imei);

	
}
