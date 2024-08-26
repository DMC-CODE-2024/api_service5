package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.ActiveImeiWithDifferentMsisdn;

@Repository
public interface ActiveImeiWithDifferentMsisdnRepo extends JpaRepository<ActiveImeiWithDifferentMsisdn, Long>{
	
	public List<ActiveImeiWithDifferentMsisdn> findTop1ByImei(String imei);
	public List<ActiveImeiWithDifferentMsisdn> findByImei(String imei);
	public List<ActiveImeiWithDifferentMsisdn> findByImeiAndImsi(String imei, String imsi);

	
}
