package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.mdr.model.app.BlackListHistory;
import com.gl.mdr.model.generic.SearchIMEIResponse;

public interface BlackListHistoryRepo extends JpaRepository<BlackListHistory, Long>, JpaSpecificationExecutor<BlackListHistory> {

	List<BlackListHistory> findByImei(String imei);

	List<BlackListHistory> findTop1ByImei(String imei);

	List<BlackListHistory> findByImeiAndImsi(String imei, String imsi);

}
