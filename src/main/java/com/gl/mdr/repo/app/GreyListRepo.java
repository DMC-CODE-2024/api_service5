package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.GreyList;

@Repository
public interface GreyListRepo extends 
JpaRepository<GreyList, Long>, JpaSpecificationExecutor<GreyList> {
	public List<GreyList> findByImei(String imei);
	//public GreyList findByImeiAndMsisdn(String imei, String msisdn);
	public List<GreyList> findByImeiAndImsi(String imei, String imsi);
	public List<GreyList> findTop1ByImei(String imei);
}
