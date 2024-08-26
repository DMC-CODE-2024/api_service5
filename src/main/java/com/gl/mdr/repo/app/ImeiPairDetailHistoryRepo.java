package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.mdr.model.app.ImeiPairDetailHistory;

public interface ImeiPairDetailHistoryRepo extends JpaRepository<ImeiPairDetailHistory, Long>, JpaSpecificationExecutor<ImeiPairDetailHistory> {

	List<ImeiPairDetailHistory> findByImei(String imei);

	List<ImeiPairDetailHistory> findTop1ByImei(String imei);

	List<ImeiPairDetailHistory> findByImeiAndImsi(String imei, String imsi);

}
