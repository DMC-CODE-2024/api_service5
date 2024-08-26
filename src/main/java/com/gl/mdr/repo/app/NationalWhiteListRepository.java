package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.NationalWhiteList;

@Repository
public interface NationalWhiteListRepository  extends 
JpaRepository<NationalWhiteList, Long>, JpaSpecificationExecutor<NationalWhiteList> {
	public List<NationalWhiteList> findByImei(String imei);
	//public NationalWhiteList findByImeiAndMsisdn(String imei, String msisdn);
	public List<NationalWhiteList> findByImeiAndImsi(String imei, String imsi);
	public List<NationalWhiteList> findTop1ByImei(String imei);
	public NationalWhiteList findByImeiIgnoreCase(String imei);
	public boolean existsByImei(String imei);
	}
