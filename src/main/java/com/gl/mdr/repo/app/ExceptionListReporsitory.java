package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.ExceptionList;

@Repository
public interface ExceptionListReporsitory extends 
JpaRepository<ExceptionList, Long>, JpaSpecificationExecutor<ExceptionList> { 
	public List<ExceptionList> findByImei(String imei);
	//public ExceptionList findByImeiAndMsisdn(String imei, String msisdn);
	public List<ExceptionList> findByImeiAndImsi(String imei, String imsi);
	public List<ExceptionList> findTop1ByImei(String imei);
}
