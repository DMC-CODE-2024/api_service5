package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.ActiveMsisdnList;

@Repository
public interface ActiveMsisdnListRepository extends JpaRepository<ActiveMsisdnList, Long>{
	public ActiveMsisdnList findByImsi(String imsi);
	public ActiveMsisdnList findByMsisdn(String msisdn);
}
